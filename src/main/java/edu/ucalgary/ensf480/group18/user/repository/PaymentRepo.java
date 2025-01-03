package edu.ucalgary.ensf480.group18.user.repository;

import edu.ucalgary.ensf480.group18.user.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
