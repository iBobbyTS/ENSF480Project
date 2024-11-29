package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seatId")
    private Seat seat;

    private int ticketPrice;

    private Boolean isPurchased;

    public Ticket(){

    }

    public Ticket(User user, Seat seat) {
        this.user = user;
        this.seat = seat;
        this.ticketPrice = seat.getSeatPrice();
        this.isPurchased = false;
    }

    public Ticket(User user, Seat seat, Boolean isPurchased) {
        this.user = user;
        this.seat = seat;
        this.ticketPrice = seat.getSeatPrice();
        this.isPurchased = isPurchased;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Boolean getPurchased() {
        return isPurchased;
    }

    public void setPurchased(Boolean purchased) {
        isPurchased = purchased;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
