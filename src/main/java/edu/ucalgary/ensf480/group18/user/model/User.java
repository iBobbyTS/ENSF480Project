package edu.ucalgary.ensf480.group18.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String usrEmail;



    @Transient
    @JsonIgnore
    protected RefundStrategy refundStrategy;

    public User() {
    }

    public User(String usrEmail) {
        this.usrEmail = usrEmail;
        this.refundStrategy = new RegularRefundStrategy();
    }

    @PostLoad
    private void initializeRefundStrategy() {
        if (refundStrategy == null) {
            refundStrategy = new RegularRefundStrategy();
        }
    }

    public Long getUserId() {
        return userId;
    }


    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public RefundStrategy getRefundStrategy() {
        if (refundStrategy == null)
            refundStrategy = new RegularRefundStrategy();

        return refundStrategy;
    }

    public void setRefundStrategy(RefundStrategy refundStrategy) {
        this.refundStrategy = refundStrategy;
    }


}
