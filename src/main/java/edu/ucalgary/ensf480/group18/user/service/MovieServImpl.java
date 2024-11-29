package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieServImpl implements MovieServ{
    @Autowired
    private MovieRepo movieRepo;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    @Override
    public Movie getMovie(int movieId) {
        return movieRepo.findById(movieId).orElse(null);
    }

    @Override
    public List<Movie> getAllMovies(LocalDate currentDate) {
        return movieRepo.findAllMoviesByDate(currentDate);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieRepo.save(movie);
    }
}
