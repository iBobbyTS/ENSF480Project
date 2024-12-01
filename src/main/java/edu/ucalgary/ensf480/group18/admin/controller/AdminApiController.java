package edu.ucalgary.ensf480.group18.admin.controller;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.service.CookieServ;
import edu.ucalgary.ensf480.group18.user.service.MovieServ;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        System.out.println(movie.getMovieId());
        System.out.println(movie.getTitle());
        System.out.println(movie.getDuration());
        System.out.println(movie.getDescription());
        System.out.println(movie.getTrailerUrl());
        System.out.println(movie.getCoverUrl());
        System.out.println(movie.getAddDate());
        movieService.addMovie(movie);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", false);
        return responseBody;
    }
}
