package edu.ucalgary.ensf480.group18.user.controller;

import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class WebpageController {
    @Autowired
    private CookieServ cookieService;
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
                model.addAttribute("VIP_message", "VIP valid until"+user.getVIPExpiry());
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
    public String bankPayment(@CookieValue(name = "TOKEN", defaultValue = "none") String userToken) {
        return "payment/bank";
    }
    @GetMapping("/movie/buy-ticket")
    public String buyTicket(@CookieValue(name = "USER_TOKEN", defaultValue = "none") String userToken) {
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
}

