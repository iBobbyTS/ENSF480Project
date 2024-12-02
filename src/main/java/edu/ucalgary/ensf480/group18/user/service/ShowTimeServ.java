package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.ShowTime;

import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.model.Theater;

import java.util.List;

public interface ShowTimeServ {
    void createShowTime(ShowTime showTime);
    List<ShowTime> getShowTimesByMovieId(int showTimeId);
    List<ShowTime> generateShowTimes(Movie movie, Theater theater);
    ShowTime getShowTimeById(int showTimeId);
}
