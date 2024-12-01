package edu.ucalgary.ensf480.group18.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class GiftCard {
    @Id
    @UuidGenerator
    private UUID giftCardId;

    private Double balance;

    private LocalDate expiryDate;

    public GiftCard() {
    }

    public GiftCard(Double balance) {
        this.balance = balance;
        // Default expiry date is 1 year from now
        this.expiryDate = LocalDate.now().plusYears(1);
    }

    public UUID getGiftCardId() {
        return giftCardId;
    }

    public void setGiftCardId(UUID giftCardId) {
        this.giftCardId = giftCardId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
