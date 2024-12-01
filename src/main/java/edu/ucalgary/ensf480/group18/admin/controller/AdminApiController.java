package edu.ucalgary.ensf480.group18.admin.controller;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.model.ShowTime;
import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import edu.ucalgary.ensf480.group18.user.service.MovieServ;
import edu.ucalgary.ensf480.group18.user.service.ShowTimeServ;
import edu.ucalgary.ensf480.group18.user.service.TheaterServ;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
    @Autowired
    private CookieServ cookieService;
    @Autowired
    private MovieServ movieService;
    @Autowired
    private TheaterServ theaterService;
    @Autowired
    private ShowTimeServ showtimeService;
    @PostMapping("/add-movie")
    public Map<String, Object> addMovie(@RequestBody Movie movie, HttpServletResponse response) {
        movie.setAddDate();
        movieService.addMovie(movie);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", 0);
        return responseBody;
    }
    @DeleteMapping("/remove-movie")
    public Map<String, Object> deleteMovie(@RequestParam(name = "movieId", required = false, defaultValue = "Guest") String movieId, HttpServletResponse response) {
        movieService.deleteMovie(Integer.parseInt(movieId));
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", 0);
        return responseBody;
    }

    @PostMapping("/add-showtime")
    public Map<String, Object> addShowtime(@RequestParam(name = "movieId", required = false, defaultValue = "Guest") String id, @CookieValue(name = "TOKEN", defaultValue = "none") String userToken, @RequestBody Map<String, Object> request) {

        Map<String, Object> responseBody = new HashMap<>();
        int movieId = Integer.parseInt(id);
        if (!cookieService.isAdmin(userToken)) {
            responseBody.put("success", 1);
            return responseBody;
        }
        if (movieId == -1) {
            responseBody.put("success", 2);
            return responseBody;
        }
        List<String> showtimes = (List<String>) request.get("showtimes");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (String showtime : showtimes) {
            ShowTime st = new ShowTime(movieService.getMovie(movieId), theaterService.getTheater(1L), LocalDateTime.parse(showtime, formatter));
            showtimeService.createShowTime(st);
        }
        responseBody.put("success", 0);
        return responseBody;
    }
}
