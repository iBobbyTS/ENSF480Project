package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieServ {
    List<Movie> getAllMovies(LocalDate currentDate);
    Movie getMovie(int movieId);
    Movie createMovie(Movie movie);

}
