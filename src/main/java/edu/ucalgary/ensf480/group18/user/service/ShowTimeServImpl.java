package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.model.ShowTime;
import edu.ucalgary.ensf480.group18.user.model.Theater;
import edu.ucalgary.ensf480.group18.user.repository.ShowTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowTimeServImpl implements ShowTimeServ {
    @Autowired
    private ShowTimeRepo showTimeRepo;

    @Override
    public void createShowTime(ShowTime showTime) {
        showTimeRepo.save(showTime);
    }

    @Override
    public List<ShowTime> getShowTimesByMovieId(int movieId) {
        return showTimeRepo.findAllByMovie_MovieId(movieId);
    }

    @Override
    public List<ShowTime> generateShowTimes(Movie movie, Theater theater) {
        List<ShowTime> showTimes = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 14; i++) { // Generate for 2 weeks (14 days)
            LocalDate currentDate = today.plusDays(i);

            // Generate 4 showtimes per day
            for (int j = 0; j < 4; j++) {
                LocalDateTime showTime = currentDate.atTime(12 + (j * 3), 0); // Showtimes at 12:00, 15:00, 18:00, 21:00
                ShowTime st = new ShowTime(movie, theater, showTime);
                showTimes.add(st);
            }
        }

        return showTimes;
    }

    public ShowTime getShowTimeById(int showTimeId) {
        return showTimeRepo.findById(showTimeId).orElse(null);
    }
}
