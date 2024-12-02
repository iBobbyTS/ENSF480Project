package edu.ucalgary.ensf480.group18.user.controller;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import edu.ucalgary.ensf480.group18.user.model.Seat;
import edu.ucalgary.ensf480.group18.user.model.ShowTime;
import edu.ucalgary.ensf480.group18.user.model.Ticket;
import edu.ucalgary.ensf480.group18.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WebpageController {
    @Autowired
    private CookieServ cookieService;
    @Autowired
    private RegisteredUserServ registeredUserService;
    @Autowired
    private MovieServ movieService;
    @Autowired
    private ShowTimeServ showTimeService;
    @Autowired
    private TicketServ ticketService;
    @Autowired
    private SeatServ seatService;
    @GetMapping("/")
    public String home(@CookieValue(name = "TOKEN", defaultValue = "none") String token, Model model) {
        // Add data to the model to display in the view
        boolean isLoggedIn = false;
        boolean isAdmin = false;
        RegisteredUser user = null;
        if (!token.equals("none")) {
            // verify token with database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        if (isLoggedIn) {
            model.addAttribute("userEmail", user.getUsrEmail());
            boolean isVIP = user.isVIP();
            if (isVIP) {
                model.addAttribute("VIP_message", "VIP valid until "+user.getVIPExpiry());
            } else {
                model.addAttribute("VIP_message", "Upgrade to VIP more benifits!");
            }
            isAdmin = user.isAdmin();
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("loggedIn", isLoggedIn);
        // Return the name of the HTML file (without the .html extension)
        return "index";
    }

    @GetMapping("/sign-up")
    public String signUp(@CookieValue(name = "TOKEN", defaultValue = "none") String token) {
        boolean isLoggedIn = false;
        if (!token.equals("none")) {
            // verify token with database
            try {
                RegisteredUser user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        if (isLoggedIn) {
            return "redirect:/";
        }
        return "sign-up";
    }

    @GetMapping("/sign-in")
    public String signIn(@CookieValue(name = "TOKEN", defaultValue = "none") String token) {
        boolean isLoggedIn = false;
        if (!token.equals("none")) {
            // verify token with database
            try {
                RegisteredUser user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        if (isLoggedIn) {
            return "redirect:/";
        }
        return "sign-in";
    }

    @GetMapping("/payment/bank")
    public String bankPayment(@CookieValue(name = "TOKEN", defaultValue = "none") String token) {
        return "payment/bank";
    }
    @GetMapping("/movie/buy-ticket")
    public String buyTicket(@CookieValue(name = "USER_TOKEN", defaultValue = "none") String token, @RequestParam("movieId") int movieId, Model model) {
        // Simulate fetching current logged-in user
        boolean isLoggedIn = false;
        RegisteredUser user = null;
        if (!token.equals("none")) {
            // verify token with database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        // Fetch movie details
        var movie = movieService.getMovie(movieId);
        if (movie == null) {
            return "redirect:/"; // Redirect to home if movie doesn't exist
        }

        // Fetch available showtimes for the movie
        List<ShowTime> showTimes = showTimeService.getShowTimesByMovieId(movieId);

        model.addAttribute("movieName", movie.getTitle());
        model.addAttribute("description", movie.getDescription());
        return "movie/buy-ticket";
    }

    @GetMapping("/vip")
    public String vip(@CookieValue(name = "TOKEN", defaultValue = "none") String token, Model model) {
        // Simulate fetching current logged-in user
        boolean isLoggedIn = false;
        RegisteredUser user = null;
        if (!token.equals("none")) {
            // verify token with database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        if (isLoggedIn) {
            model.addAttribute("userEmail", user.getUsrEmail());
            LocalDate vipRenewalDate = user.getVIPLastRenewal();
            if (vipRenewalDate != null) {
                LocalDate vipExpiryDate = vipRenewalDate.plusYears(1);
                if (vipExpiryDate.isAfter(LocalDate.now())) {
                    model.addAttribute("vipStatus", "valid");
                    model.addAttribute("vipMessage", "VIP valid until " + vipExpiryDate);
                } else {
                    model.addAttribute("vipStatus", "expired");
                    model.addAttribute("vipMessage", "VIP expired on " + vipExpiryDate);
                }
            } else {
                model.addAttribute("vipStatus", "none");
                model.addAttribute("vipMessage", "");
            }
        }
        return "vip";
    }
    @GetMapping("/vip-payment")
    public String vipPayment(@CookieValue(name = "TOKEN", defaultValue = "none") String token, Model model) {
        // Simulate fetching current logged-in user
        boolean isLoggedIn = false;
        RegisteredUser user = null;
        if (!token.equals("none")) {
            // verify token with database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        if (!isLoggedIn) {
            return "redirect:/sign-in";
        }
        // Check if user has payment method, if not redirect to add payment method with url parameter, so it can redirect back here
        if (user.getCard() == null) {
            return "redirect:/payment/add?redirect=/vip-payment";
        }
        LocalDate vipRenewalDate = user.getVIPLastRenewal();
        LocalDate vipExpiryDate;
        // If user is already VIP, extend the expiry date, else set it to 1 year from now
        if (vipRenewalDate != null && vipRenewalDate.plusYears(1).isAfter(LocalDate.now())) {
            vipExpiryDate = vipRenewalDate.plusYears(1);
        } else {
            vipExpiryDate = LocalDate.now().plusYears(1);
        }
        String cardNumber = user.getCard().getCardNum();
        model.addAttribute("email", user.getUsrEmail());
        model.addAttribute("paymentMethod", "**** " + cardNumber.substring(cardNumber.length() - 4));
        model.addAttribute("vipExpiry", vipExpiryDate);
        model.addAttribute("total", "$20");

        return "vip-payment"; // Thymeleaf template name
    }
    @GetMapping("/payment/add")
    public String addPaymentMethod() {
        return "/payment/add"; // Thymeleaf template name
    }

    @GetMapping("payment/done/vip")
    public String vipPaymentResult(
            @CookieValue(name = "TOKEN", defaultValue = "none") String token,
            @RequestParam(name = "success", defaultValue = "-1") String successCode,
            Model model
    ) {
        boolean isLoggedIn = false;
        RegisteredUser user = null;

        if (!token.equals("none")) {
            // Verify token with the database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }

        if (!isLoggedIn) {
            return "redirect:/sign-in"; // Redirect to sign-in if not logged in
        }

        int success = Integer.parseInt(successCode);

        if (success == 0) {
            // Handle successful payment
            LocalDate vipExpiryDate;
            // if user is already VIP, set last renewal to lastrenewal+ 1 year, else set it to 1 year from now
            if (user.getVIPLastRenewal() != null && user.getVIPLastRenewal().plusYears(1).isAfter(LocalDate.now())) {
                vipExpiryDate = user.getVIPLastRenewal().plusYears(1);
            } else {
                vipExpiryDate = LocalDate.now().plusYears(1);
            }

            user.renewVIP(vipExpiryDate); // Update user data
            registeredUserService.updateUser(user); // Persist changes to the database

            model.addAttribute("success", true);
            model.addAttribute("vipExpiry", vipExpiryDate);
        } else {
            // Handle failed payment
            model.addAttribute("success", false);
        }

        return "payment/done/vip"; // Shared Thymeleaf template
    }

    @GetMapping("/payment/done/ticket")
    public String ticketPaymentResult(
            @CookieValue(name = "TOKEN", defaultValue = "none") String token,
            @RequestParam(name = "success", defaultValue = "-1") String successCode,
            @RequestParam(name = "showtimeId") int showtimeId,
            @RequestParam(name = "seats") String seatsJson,
            Model model
    ) {
        System.out.println("Ticket payment result");
        boolean isLoggedIn = false;
        RegisteredUser user = null;

        if (!token.equals("none")) {
            // Verify token with the database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }

        if (!isLoggedIn) {
            user = new RegisteredUser();
            user.setUsrEmail("guest");
        }

        int success = Integer.parseInt(successCode);

        if (success == 0) {
            seatsJson = URLDecoder.decode(seatsJson, StandardCharsets.UTF_8);
            List<List<Integer>> seatsArray = new ArrayList<>();
            for (String seat : seatsJson.split(",")) {
                String[] seatParts = seat.split("-");
                List<Integer> seatCoords = new ArrayList<>();
                seatCoords.add(Integer.parseInt(seatParts[0]));
                seatCoords.add(Integer.parseInt(seatParts[1]));
                seatsArray.add(seatCoords);
            }
            // Store tickets in the database

            ShowTime showtime = showTimeService.getShowTimeById(showtimeId);
            StringBuilder ticketIds = new StringBuilder();
            for (List<Integer> seat : seatsArray) {
                Seat newSeat = new Seat(seat.get(0), seat.get(1), 25, true, showtime);
                System.out.println(newSeat.getShowTime());
                seatService.createSeat(newSeat); // Save the Seat entity first
                Ticket t = new Ticket(user.getUsrEmail(), newSeat, true);
                ticketIds.append(t.getTicketId()).append(", ");
                System.out.println(t.getTicketId());
                System.out.println(t.getTicketId());
                System.out.println(t.getTicketId());
                System.out.println(t.getTicketId());
                ticketService.createTicket(t);
            }

            // Prepare success page data
            model.addAttribute("success", true);
            model.addAttribute("movieTitle", showtime.getMovie().getTitle());
            model.addAttribute("showtime", showtime.getShowTime().toString());
            model.addAttribute("ticketId", ticketIds.substring(0, ticketIds.length() - 2));
        } else {
            // Redirect to the buy-ticket page for this movie
            return "redirect:/movie/buy-ticket?movieId=" + showTimeService.getShowTimeById(showtimeId).getMovie().getMovieId();
        }

        return "payment/done/ticket";
    }

    @GetMapping("/my-tickets")
    public String myTickets(@CookieValue(name = "TOKEN", defaultValue = "none") String token, Model model) {
        boolean isLoggedIn = false;
        RegisteredUser user = null;
        if (!token.equals("none")) {
            // verify token with database
            try {
                user = cookieService.getUser(token);
                isLoggedIn = true;
            } catch (IllegalArgumentException e) {
                // Invalid token
            }
        }
        if (!isLoggedIn) {
            return "redirect:/sign-in";
        }
        return "my-tickets";
    }

    @GetMapping("/cancel-ticket")
    public String cancelTicket() {
        return "cancel-ticket";
    }
}

