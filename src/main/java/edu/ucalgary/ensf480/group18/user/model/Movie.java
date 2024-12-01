package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ShowTime> showTimes = new ArrayList<>(); // Best practice: Initialize the list

    private String title;

    private int duration;
    private String description;
    private String trailerUrl;
    private String coverUrl;
    private LocalDate addDate;

    // Default constructor (required by JPA)
    public Movie() {
        // No explicit initialization needed since showTimes is already initialized above
    }

    // Parameterized constructor for easier object creation
    public Movie(String title, int duration, String description, String trailerUrl, String coverUrl, LocalDate addDate) {
        this.title = title;
        this.duration = duration;
        this.description = description;
        this.trailerUrl = trailerUrl;
        this.coverUrl = coverUrl;
        this.addDate = addDate;
    }

    // Getters and setters
    public int getMovieId() {
        return movieId;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }

    public void addShowTime(ShowTime showTime) {
        showTimes.add(showTime);
        showTime.setMovie(this);
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public void setAddDate() {
        this.addDate = LocalDate.now();
    }

}
