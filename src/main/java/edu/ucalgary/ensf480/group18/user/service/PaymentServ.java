package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.GiftCard;
import edu.ucalgary.ensf480.group18.user.model.Payment;

public interface PaymentServ {
    Payment createPayment(Payment payment);
    Payment getPaymentById(Long paymentId);
    void deletePayment(Payment payment);
    Payment updatePayment(Payment payment);

    GiftCard refundPayment(Payment payment);
}
