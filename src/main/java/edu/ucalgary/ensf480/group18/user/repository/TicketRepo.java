package edu.ucalgary.ensf480.group18.user.repository;

import edu.ucalgary.ensf480.group18.user.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TicketRepo extends JpaRepository<Ticket, UUID> {
    @Query("SELECT t FROM Ticket t WHERE t.usrEmail = ?1")
    List<Ticket> findTicketsByEmail(String emailAddress);

}
