package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Movie;

import java.util.List;

public interface MovieServ {
    List<Movie> getAllMovies();
    List<Movie> searchMovies(String title);
    Movie getMovie(int movieId);
    void addMovie(Movie movie);
    void deleteMovie(int movieId);
    Movie updateMovie(Movie movie);

}
