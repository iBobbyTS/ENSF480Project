package edu.ucalgary.ensf480.group18.admin.controller;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.service.MovieServ;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
    @Autowired
    private MovieServ movieService;
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
}
