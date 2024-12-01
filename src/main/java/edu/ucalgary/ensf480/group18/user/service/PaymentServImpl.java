package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.GiftCard;
import edu.ucalgary.ensf480.group18.user.model.Payment;
import edu.ucalgary.ensf480.group18.user.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServImpl implements PaymentServ {
    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private GiftCardServ giftCardServ;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepo.findById(paymentId).orElse(null);
    }

    @Override
    public void deletePayment(Payment payment) {
        paymentRepo.delete(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    // TODO: Implement refundPayment
    @Override
    public GiftCard refundPayment(Payment payment) {
        double refundAmount = payment.getTicket().getTicketPrice();
        //Subtract admin fee based on User refund strategy
        refundAmount = payment.getCard().getUser().getRefundStrategy().calculateRefund(refundAmount);
        //Create a gift card with the refund amount
        return giftCardServ.createGiftCard(new GiftCard(refundAmount));
    }





}
