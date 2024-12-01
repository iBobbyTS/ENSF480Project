package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServImpl implements MovieServ{
    @Autowired
    private MovieRepo movieRepo;

    @Override
    public void addMovie(Movie movie) {
        movieRepo.save(movie);
    }

    @Override
    public void deleteMovie(int movieId) {
        movieRepo.deleteById(movieId);
    }

    @Override
    public Movie getMovie(int movieId) {
        return movieRepo.findById(movieId).orElse(null);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    @Override
    public List<Movie> searchMovies(String title) {
        return movieRepo.findAllByTitle(title);
    }
}
