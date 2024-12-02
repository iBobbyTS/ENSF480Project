package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import edu.ucalgary.ensf480.group18.user.repository.RegisteredUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisteredUserServImpl implements RegisteredUserServ {
    // Registered user service implementation
    @Autowired
    private RegisteredUserRepo registeredUserRepo;

    @Override
    public void createUser(RegisteredUser registeredUser) {
        registeredUserRepo.save(registeredUser);
    }

    @Override
    public RegisteredUser getUserByEmailAddress(String emailAddress) {
        return registeredUserRepo.findByEmailAddress(emailAddress);
    }
    public boolean emailExists(String email) {
        return registeredUserRepo.findByUsrEmail(email) != null;
    }

    public boolean verifyUser(String email, String password) {
        RegisteredUser user = registeredUserRepo.findByUsrEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    public void updateUser(RegisteredUser user) {
        registeredUserRepo.save(user);
    }
}
