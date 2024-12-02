package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.GiftCard;

import java.util.UUID;

public interface GiftCardServ {
    GiftCard createGiftCard(GiftCard giftCard);
    GiftCard getGiftCard(UUID giftCardId);
    void deleteGiftCard(GiftCard giftCard);
    void updateGiftCard(GiftCard giftCard);
}
