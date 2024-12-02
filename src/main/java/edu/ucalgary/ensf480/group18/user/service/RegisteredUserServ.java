package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;

public interface RegisteredUserServ {
    void createUser(RegisteredUser user);
    RegisteredUser getUserByEmailAddress(String emailAddress);
    boolean emailExists(String email);
    boolean verifyUser(String email, String password);
    void updateUser(RegisteredUser user);
}

