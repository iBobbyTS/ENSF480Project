package edu.ucalgary.ensf480.group18.admin.controller;
import java.util.List;

import edu.ucalgary.ensf480.group18.user.model.ShowTime;
import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import edu.ucalgary.ensf480.group18.user.service.MovieServ;
import edu.ucalgary.ensf480.group18.user.service.ShowTimeServ;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminWebpageController {
    @Autowired
    private CookieServ cookieService;
    @Autowired
    private MovieServ movieService;
    @Autowired
    private ShowTimeServ showTimeService;
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

    @GetMapping("/add-showtime")
    public String addShowtime(@RequestParam(name = "id", required = false, defaultValue="-1") String id, @CookieValue(name = "TOKEN", defaultValue = "none") String userToken, Model model, HttpServletResponse response) {
        int movieId = Integer.parseInt(id);
        if (!cookieService.isAdmin(userToken)) {
            return "redirect:/";
        }
        if (movieId == -1) {
            return "redirect:/admin";
        }
        String movieName = movieService.getMovie(movieId).getTitle();
        List<ShowTime> showtimes = showTimeService.getShowTimesByMovieId(movieId);
        String[] showtimesString = new String[showtimes.size()];
        for (int i = 0; i < showtimes.size(); i++) {
            showtimesString[i] = showtimes.get(i).getShowTime().toString().replace('T', ' ');
        }

        model.addAttribute("movieName", movieName);
        model.addAttribute("showtimes", showtimesString);
        return "admin/add-showtime";
    }

}
