package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.GiftCard;
import edu.ucalgary.ensf480.group18.user.repository.GiftCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GiftCardServImpl implements GiftCardServ {

    @Autowired
    private GiftCardRepo giftCardRepo;

    @Override
    public GiftCard createGiftCard(GiftCard giftCard) {
        return giftCardRepo.save(giftCard);
    }

    @Override
    public GiftCard getGiftCard(UUID giftCardId) {
        return giftCardRepo.findById(giftCardId).orElse(null);
    }
}
