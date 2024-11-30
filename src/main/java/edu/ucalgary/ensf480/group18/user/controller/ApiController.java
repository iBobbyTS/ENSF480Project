package edu.ucalgary.ensf480.group18.user.controller;

import java.util.*;

import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import edu.ucalgary.ensf480.group18.user.service.CookieServImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import edu.ucalgary.ensf480.group18.user.service.RegisteredUserServ;

// temp class
class Movie {
    private String title;
    private String cover;
    private String actors;
    private String duration;

    public Movie(String title, String cover, String actors, String duration) {
        this.title = title;
        this.cover = cover;
        this.actors = actors;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }
}

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

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return List.of(
                new Movie("Movie 1", "cover1.jpg", "Actor 1, Actor 2", "120 min"),
                new Movie("Movie 2", "cover2.jpg", "Actor 3, Actor 4", "90 min"),
                new Movie("Movie 3", "cover3.jpg", "Actor 5, Actor 6", "110 min"),
                new Movie("Movie 4", "cover4.jpg", "Actor 7, Actor 8", "100 min"),
                new Movie("Movie 5", "cover5.jpg", "Actor 9, Actor 10", "130 min"),
                new Movie("Movie 6", "cover6.jpg", "Actor 11, Actor 12", "140 min"),
                new Movie("Movie 7", "cover7.jpg", "Actor 13, Actor 14", "150 min"),
                new Movie("Movie 8", "cover8.jpg", "Actor 15, Actor 16", "160 min"),
                new Movie("Movie 9", "cover9.jpg", "Actor 17, Actor 18", "170 min"),
                new Movie("Movie 10", "cover10.jpg", "Actor 19, Actor 20", "180 min"),
                new Movie("Movie 11", "cover11.jpg", "Actor 21, Actor 22", "190 min"),
                new Movie("Movie 12", "cover12.jpg", "Actor 23, Actor 24", "200 min"),
                new Movie("Movie 13", "cover13.jpg", "Actor 25, Actor 26", "210 min"),
                new Movie("Movie 14", "cover14.jpg", "Actor 27, Actor 28", "220 min"),
                new Movie("Movie 15", "cover15.jpg", "Actor 29, Actor 30", "230 min"),
                new Movie("Movie 16", "cover16.jpg", "Actor 31, Actor 32", "240 min"),
                new Movie("Movie 17", "cover17.jpg", "Actor 33, Actor 34", "250 min")
        );
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

    @PostMapping("/sign-in")
    public Map<String, Object> handleSignIn(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Replace with actual database verification logic
        if ("user@example.com".equals(email) && "securePassword".equals(password)) {
            // Success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", 0);
            response.put("message", "Login successful");
            return response;
        }

        // Failure response
        Map<String, Object> response = new HashMap<>();
        response.put("success", 1);
        response.put("message", "Invalid email or password");
        return response;
    }
}
