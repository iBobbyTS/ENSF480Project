package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatID;
    private int seatRow;
    private int seatColumn;
    private int seatPrice;
    @Column(name = "isReserved")
    private Boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "showTimeId")
    @JsonBackReference
    private ShowTime showTime;

    public Seat() {
    }

    public Seat(int seatRow, int seatColumn, int seatPrice, Boolean isReserved) {
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.seatPrice = seatPrice;
        this.isReserved = isReserved;
    }

    public Long getSeatID() {
        return seatID;
    }

    public void setSeatID(Long seatID) {
        this.seatID = seatID;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public int getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(int seatPrice) {
        this.seatPrice = seatPrice;
    }

    public ShowTime getShowTime() {
        return showTime;
    }

    public void setShowTime(ShowTime showTime) {
        this.showTime = showTime;
    }
}
