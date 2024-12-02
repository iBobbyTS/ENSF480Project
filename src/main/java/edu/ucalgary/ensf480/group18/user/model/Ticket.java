package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Ticket {
    @Id
    @UuidGenerator
    private UUID ticketId;

    private String usrEmail;

    @ManyToOne
    @JoinColumn(name = "seatId")
    private Seat seat;

    private int ticketPrice;

    private Boolean isPurchased;

    public Ticket(){

    }

    public Ticket(String usrEmail, Seat seat) {
        this.usrEmail = usrEmail;
        this.seat = seat;
        this.ticketPrice = seat.getSeatPrice();
        this.isPurchased = false;
    }

    public Ticket(String usrEmail, Seat seat, Boolean isPurchased) {
        this.usrEmail = usrEmail;
        this.seat = seat;
        this.ticketPrice = seat.getSeatPrice();
        this.isPurchased = isPurchased;
        this.ticketId = UUID.randomUUID();
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }


    public String getUserEmail() {
        return usrEmail;
    }

    public void setUser(String usrEmail) {
        this.usrEmail = usrEmail;
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
