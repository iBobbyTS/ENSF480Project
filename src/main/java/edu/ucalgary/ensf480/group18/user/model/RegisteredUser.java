package edu.ucalgary.ensf480.group18.user.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RegisteredUser {

    @Id
    @Column(unique = true, nullable = false)
    private String usrEmail;
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0.0")
    private float credit;
    private String password;
//    @Column(nullable = true)
    private LocalDate VIPLastRenewal;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean admin;

    @OneToOne(mappedBy = "user")
    private Card card;

    public RegisteredUser() {
    }

    public RegisteredUser(String usrEmail, String password, boolean admin) {
        this.usrEmail = usrEmail;
        this.password = password;
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isVIP() {
        return VIPLastRenewal != null && VIPLastRenewal.isAfter(LocalDate.now().minusYears(1));
    }

    public String getVIPExpiry() {
        if (VIPLastRenewal == null) {
            return "Not VIP";
        }
        return VIPLastRenewal.plusYears(1).toString();
    }

    public void renewVIP() {
        this.VIPLastRenewal = LocalDate.now();
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public void useCredit(float amount) {
        this.credit -= amount;
    }

    public void addCredit(float amount) {
        this.credit += amount;
    }
}
