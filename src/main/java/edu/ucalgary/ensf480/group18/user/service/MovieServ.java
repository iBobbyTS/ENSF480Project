package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface MovieServ {
    List<Movie> getAllMovies();
    List<Movie> searchMovies(String title);
    Movie getMovie(int movieId);
    Movie addMovie(Movie movie);

    Movie updateMovie(Movie movie);

}
