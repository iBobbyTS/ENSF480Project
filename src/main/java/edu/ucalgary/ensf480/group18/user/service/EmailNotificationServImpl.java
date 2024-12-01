package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.GiftCard;
import edu.ucalgary.ensf480.group18.user.model.MovieObserver;
import edu.ucalgary.ensf480.group18.user.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServImpl implements EmailNotificationServ{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendNewMovieEmail(MovieObserver movieObserver) {
        System.out.println();
    }

    @Override
    public void emailTicket(Ticket ticket) {
        // Send the ticket via email
        if (ticket != null && ticket.getUser().getUsrEmail() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(ticket.getUser().getUsrEmail());
            message.setSubject("Your Ticket Details");
            message.setText(buildTicketEmailBody(ticket));
            mailSender.send(message);
        }
    }

    public void emailGiftCard(String email, GiftCard giftCard) {
        // Send the gift card via email
        if (email != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your Gift Card Details");
            message.setText(buildGiftCardEmailBody(giftCard));
            mailSender.send(message);
        }
    }

    private String buildTicketEmailBody(Ticket ticket) {
        return "Ticket Details: \n" +
                "Ticket ID: " + ticket.getTicketId() + "\n" +
                "Movie: " + ticket.getSeat().getShowTime().getMovie().getTitle() + "\n" +
                "Show Time: " + ticket.getSeat().getShowTime().getShowTime() + "\n" +
                "Seat Number: " + ticket.getSeat().getSeatNumber() + "\n" +
                "Price: " + ticket.getTicketPrice() + "\n" +
                "Enjoy the movie!";
    }

    private String buildGiftCardEmailBody(GiftCard giftCard) {
        return "Gift Card Details: \n" +
                "Gift Card Number: " + giftCard.getGiftCardId() + "\n" +
                "Amount: " + giftCard.getBalance() + "\n" +
                "Expiry Date: " + giftCard.getExpiryDate() + "\n" +
                "Enjoy your gift card!";
    }
}
