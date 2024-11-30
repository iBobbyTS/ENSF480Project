package edu.ucalgary.ensf480.group18.user.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Cookie {
    @Id
    @Column(unique = true, nullable = false)
    private String token;

    private LocalDate addDate;

    @ManyToOne
    @JoinColumn(name = "usrEmail", nullable = false)
    private RegisteredUser user;

    public Cookie() {

    }
    public Cookie(String token, LocalDate addDate, RegisteredUser user) {
        this.token = token;
        this.addDate = addDate;
        this.user = user;
    }

    // Getters and Setters
    public LocalDate getAddDate() {
        return addDate;
    }

    public RegisteredUser getUser() {
        return user;
    }
}
