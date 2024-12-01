package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.User;
import edu.ucalgary.ensf480.group18.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServImpl implements UserServ {

    @Autowired
    private UserRepo userRepo;
    
    @Override
    public User createUser(String userEmail) {
        return userRepo.save(new User(userEmail));
    }


    @Override
    public User getUserByEmailAddress(String emailAddress) {
        return userRepo.findByEmailAddress(emailAddress);
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }
}
