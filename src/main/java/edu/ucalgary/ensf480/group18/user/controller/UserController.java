package edu.ucalgary.ensf480.group18.user.controller;

import edu.ucalgary.ensf480.group18.user.model.Card;
import edu.ucalgary.ensf480.group18.user.model.RegisteredUser;
import edu.ucalgary.ensf480.group18.user.model.Ticket;
import edu.ucalgary.ensf480.group18.user.model.User;
import edu.ucalgary.ensf480.group18.user.service.*;
import jdk.jfr.Registered;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServ userService;

    @Autowired
    private RegisteredUserServ registeredUserService;

    @Autowired
    private TicketServ ticketService;

    @Autowired
    private AddressServ addressService;

    @Autowired
    private CardServ cardService;

    @GetMapping("/{UsrEmail}")
    public User findUser(@PathVariable String UsrEmail){
        return userService.getUserByEmailAddress(UsrEmail);
    }

    @GetMapping("/getTickets/{UsrEmail}")
    public List<Ticket> findRegisteredUserTickets(@PathVariable String UsrEmail){
        return ticketService.getTicketByEmailAddress(UsrEmail);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam String userEmail) {
        // if user email is already exist throw error
        if(userService.getUserByEmailAddress(userEmail) != null) {
            return ResponseEntity.badRequest().body(userEmail); // Return 400 with error message
        }
        try {
            User createdUser = userService.createUser(userEmail);
            return ResponseEntity.ok(createdUser); // Return 200 with created user
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return 400 with error message
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred."); // Return 500 with generic error message
        }
    }
//    @PostMapping("/registeredUsers/add")
//    public RegisteredUser addRegisteredUser(@RequestBody RegisteredUser registeredUser){
//        addressService.createAddress(registeredUser.getAddress());
//        Card card = cardService.createCard(registeredUser.getCard());
//        RegisteredUser newUser = registeredUserService.createUser(registeredUser);
//        card.setUser(registeredUser);
//        cardService.updateCard(card);
//        return newUser;
//    }


}
