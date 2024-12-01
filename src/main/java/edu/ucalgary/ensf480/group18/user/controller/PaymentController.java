package edu.ucalgary.ensf480.group18.user.controller;

import edu.ucalgary.ensf480.group18.user.model.*;
import edu.ucalgary.ensf480.group18.user.service.CardServ;
import edu.ucalgary.ensf480.group18.user.service.EmailNotificationServ;
import edu.ucalgary.ensf480.group18.user.service.PaymentServ;
import edu.ucalgary.ensf480.group18.user.service.TicketServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentServ paymentService;

    @Autowired
    private CardServ cardService;

    @Autowired
    private TicketServ ticketService;

    @Autowired
    private EmailNotificationServ emailNotificationService;

    @PostMapping("/create")
    public Payment createPayment(@RequestParam UUID ticketId, @RequestParam String cardNum){
        try{
            if (ticketId == null || cardNum == null)
                throw new IllegalArgumentException("Ticket and card must be provided");

            Card card = cardService.getCard(cardNum);
            Ticket ticket = ticketService.getTicket(ticketId);
            if (card == null)
                throw new IllegalArgumentException("Card not found");
            if (ticket == null)
                throw new IllegalArgumentException("Ticket not found");

            Payment payment = paymentService.createPayment(new Payment(ticket, card));
            ticket.setPurchased(true);
            ticketService.updateTicket(ticket);
            //Email the ticket to the user
            emailNotificationService.emailTicket(ticket);

            return payment;
        }
        catch (Exception e){
            return null;
        }

    }

    @PostMapping("/ticket/cancel")
    public void cancelTicket(@RequestParam UUID ticketId){
        Ticket ticket = ticketService.getTicket(ticketId);
        if (ticket == null)
            throw new IllegalArgumentException("Ticket not found");
        Seat seat = ticket.getSeat();
        seat.setIsReserved(false);
        ticketService.deleteTicket(ticket);
    }

    @PostMapping("/{paymentId}/refund")
    public GiftCard refundPayment(@PathVariable Long paymentId){
        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment == null)
            throw new IllegalArgumentException("Payment not found");

        if(payment.getRefunded())
            throw new IllegalArgumentException("Payment has already been refunded");
        LocalDateTime showTime = payment.getTicket().getSeat().getShowTime().getShowTime();
        //Only refund if the show time is more than 24 hours away
        if (LocalDateTime.now().isBefore(showTime.minusDays(3))){
            GiftCard giftCard = paymentService.refundPayment(payment);
            emailNotificationService.emailGiftCard(payment.getCard().getUser().getUsrEmail(), giftCard);
            return giftCard;
        }
        else {
            throw new IllegalArgumentException("Cannot refund payment within 3 days of show time");
        }

    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException e){
        return e.getMessage();
    }

}
