package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Cookie;
import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;

import java.time.LocalDate;

public interface CookieServ {

    /**
     * Adds a new cookie entry for the given user.
     *
     * @param user the user for whom the cookie is generated.
     * @return the created Cookie object.
     */
    Cookie addRow(String token, RegisteredUser user);

    /**
     * Retrieves the add date for a given token.
     *
     * @param token the unique token of the cookie.
     * @return the date the cookie was added.
     */
    LocalDate getAddDate(String token);

    /**
     * Retrieves the user email associated with a given token.
     *
     * @param token the unique token of the cookie.
     * @return the email of the user associated with the token.
     */
    String getUserEmail(String token);

    RegisteredUser getUser(String token);
}
