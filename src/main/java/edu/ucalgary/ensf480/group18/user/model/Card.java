package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class Card {
    @Id
    private String cardNum;

    private LocalDate expiry;
    private String cvc;

    private String cardHolder;

    @OneToOne
    @JoinColumn(name = "usrEmail")
    @JsonIgnore
    private RegisteredUser user;

    public Card(){}
    public Card(String cardNum, LocalDate expiry, String cvc, String cardHolder, RegisteredUser user) {
        this.cardNum = cardNum;
        this.expiry = expiry;
        this.cvc = cvc;
        this.cardHolder = cardHolder;
        this.user = user;
    }

    public String getCardNum() {
        return cardNum;
    }
    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
    public LocalDate getExpiry() {
        return expiry;
    }
    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }
    public String getCvc() {
        return cvc;
    }
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public RegisteredUser getUser() {
        return user;
    }
    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return this.cardNum;
    }



}

