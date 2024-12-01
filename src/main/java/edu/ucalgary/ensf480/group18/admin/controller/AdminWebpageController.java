package edu.ucalgary.ensf480.group18.admin.controller;

import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminWebpageController {
    @Autowired
    private CookieServ cookieService;
    @GetMapping
    public String adminDashboard(@CookieValue(name = "TOKEN", defaultValue = "none") String userToken, Model model) {
        if (!cookieService.isAdmin(userToken)) {
            return "redirect:/";
        }
        return "admin/index";
    }

    @GetMapping("/add-movie")
    public String addMovie(@CookieValue(name = "TOKEN", defaultValue = "none") String userToken, Model model) {
        if (!cookieService.isAdmin(userToken)) {
            return "redirect:/";
        }
        return "admin/add-movie";
    }

}
