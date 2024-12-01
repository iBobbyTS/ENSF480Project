package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showTimeId;

    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    @JsonBackReference
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theaterId")
    private Theater theater;

    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Seat> seats;

    @Column(nullable = false)
    private LocalDateTime showTime;

    public ShowTime() {
    }

    public ShowTime(Movie movie, Theater theater, LocalDateTime showTime) {
        this.movie = movie;
        this.theater = theater;
        this.showTime = showTime;
    }

    public int getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(int showTimeId) {
        this.showTimeId = showTimeId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}

