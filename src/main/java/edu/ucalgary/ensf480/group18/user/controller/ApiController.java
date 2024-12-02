package edu.ucalgary.ensf480.group18.user.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import edu.ucalgary.ensf480.group18.user.model.*;
import edu.ucalgary.ensf480.group18.user.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


class UserAccount {
    private String email;
    private String password;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private RegisteredUserServ registeredUserService;
    @Autowired
    private CookieServ cookieService;
    @Autowired
    private MovieServ movieService;
    @Autowired
    private CardServ cardService;
    @Autowired
    private SeatServ seatService;
    @Autowired
    private ShowTimeServ showTimeService;
    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }
    @PostMapping("/sign-up")
    public Map<String, Object> signUp(@RequestBody UserAccount userAccount, HttpServletResponse response) {
        // check if email exists
        Map<String, Object> responseBody = new HashMap<>();
        if (registeredUserService.emailExists(userAccount.getEmail())) {
            // return {"status": 1, "message": "Email already exists"}
            responseBody.put("status", 1);
            responseBody.put("message", "Email already exists");
        } else {
            // if not, return {"status": 0, "message": "User created"}
            RegisteredUser newUser = new RegisteredUser(userAccount.getEmail(), userAccount.getPassword(), false);
            registeredUserService.createUser(newUser);
            // Generate token (for simplicity, using a UUID here)
            String token = UUID.randomUUID().toString();
            // Set token as a cookie
            Cookie cookie = new Cookie("TOKEN", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(15552000); // 6 months in seconds
            response.addCookie(cookie);
            // store token in database
            cookieService.addRow(token, newUser);
            responseBody.put("status", 0);
            responseBody.put("message", "User created");
        }
        return responseBody;
    }

    @DeleteMapping("/sign-out")
    public ResponseEntity<Map<String, Object>> signOut(@CookieValue(name = "TOKEN", defaultValue = "none") String token, HttpServletResponse response) {
        // Clear the token cookie
        Cookie cookie = new Cookie("TOKEN", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        // Remove the token from the database
        cookieService.deleteByToken(token);

        return ResponseEntity.ok(Map.of("status", 0, "message", "Logged out successfully"));
    }

    @PostMapping("/sign-in")
//    public Map<String, Object> handleSignIn(@RequestBody Map<String, String> credentials) {
    public Map<String, Object> signUp(@RequestBody Map<String, String> credentials, HttpServletResponse response) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        // verify email and password
        Map<String, Object> responseBody = new HashMap<>();
        System.out.println("Log in");
        if (registeredUserService.verifyUser(email, password)) {
            System.out.println("Logged in successfully");
            // Success response
            responseBody.put("success", 0);
            responseBody.put("message", "Logged in successfully");
            // Generate token (for simplicity, using a UUID here)
            String token = UUID.randomUUID().toString();
            // Set token as a cookie
            Cookie cookie = new Cookie("TOKEN", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(15552000); // 6 months in seconds
            response.addCookie(cookie);
            // store token in database
            cookieService.logExistingUserInByEmail(token, email);
        } else {
            // Failure response
            responseBody.put("success", 1);
            responseBody.put("message", "Invalid email or password");
        }
        return responseBody;
    }
    @PostMapping("/add-payment-method")
    @ResponseBody
    public Map<String, Object> addPaymentMethod(@CookieValue(name = "TOKEN", defaultValue = "none") String token, @RequestBody Map<String, String> paymentInfo) {
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
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "You must be logged in to add a payment method.");
            return response;
        }
        // Validate payment info
        String cardNumber = paymentInfo.get("cardNumber");
        String expiryDate = paymentInfo.get("expiryDate");
        String cvc = paymentInfo.get("cvc");
        String name = paymentInfo.get("name");

        Map<String, Object> response = new HashMap<>();

        if (cardNumber == null || cardNumber.length() != 16) {
            response.put("success", false);
            response.put("message", "Invalid card number.");
            return response;
        }

        if (expiryDate == null || expiryDate.isEmpty() || !expiryDate.matches("\\d{2}/\\d{2}")) {
            response.put("success", false);
            response.put("message", "Invalid expiry date.");
            return response;
        }

        if (cvc == null || cvc.length() != 3) {
            response.put("success", false);
            response.put("message", "Invalid CVC.");
            return response;
        }

        if (name == null || name.isEmpty()) {
            response.put("success", false);
            response.put("message", "Invalid name.");
            return response;
        }

        // Simulate saving payment info to the database
        LocalDate expiryDateLocal = LocalDate.parse("01/"+expiryDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
        cardService.createCard(new Card(cardNumber, expiryDateLocal, cvc, name, user));


        response.put("success", true);
        response.put("message", "Payment method added successfully.");
        return response;
    }
    @GetMapping("/seats")
    public List<Seat> getSeats(@RequestParam("showtimeId") int showtimeId) {
        // Fetch seat data based on the showtime ID
        ShowTime showtime = showTimeService.getShowTimeById(showtimeId);
        return seatService.getAllSeats(showtime);
    }

    @GetMapping("/showtimes")
    public List<ShowTime> getShowTimes(@RequestParam("movieId") int movieId) {
        return showTimeService.getShowTimesByMovieId(movieId);
    }
}
