package edu.ucalgary.ensf480.group18.user.controller;

import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    public String signUp(@CookieValue(name = "TOKEN", defaultValue = "none") String userToken) {
        // Check if signed in with the cookie
//                if (true) { // Simulate valid token check
//            return "redirect:/"; // Redirect to home page if logged in
//        }

        return "sign-up";
    }

    @GetMapping("/sign-in")
    public String signIn(@CookieValue(name = "TOKEN", defaultValue = "none") String userToken) {
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
}

