package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketServ {
    void createTicket(Ticket ticket);
    Ticket getTicket(UUID ticketId);
    List<Ticket> getTicketByEmailAddress(String emailAddress);

    Ticket updateTicket(Ticket ticket);
    void deleteTicket(Ticket ticket);
}
