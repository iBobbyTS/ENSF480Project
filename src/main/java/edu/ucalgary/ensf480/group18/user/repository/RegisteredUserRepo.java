package edu.ucalgary.ensf480.group18.user.repository;

import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("registeredUserRepo")
public interface RegisteredUserRepo extends JpaRepository<RegisteredUser, String> {
    // Registered user repository
    // Extends user repository
    @Query("SELECT u FROM RegisteredUser u WHERE u.usrEmail = ?1")
    RegisteredUser findByEmailAddress(String emailAddress);
    RegisteredUser findByUsrEmail(String usrEmail);
}
