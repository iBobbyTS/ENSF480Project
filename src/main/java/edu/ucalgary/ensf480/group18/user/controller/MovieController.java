package edu.ucalgary.ensf480.group18.user.controller;

import edu.ucalgary.ensf480.group18.user.model.*;
import edu.ucalgary.ensf480.group18.user.model.Movie;
import edu.ucalgary.ensf480.group18.user.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieServ movieService;

    @Autowired
    private ShowTimeServ showTimeService;

    @Autowired
    private SeatServ seatService;

    @Autowired
    private UserServ userService;

    @Autowired
    private TicketServ ticketService;

    @Autowired
    private TheaterServ theaterService;

//    @GetMapping("/getAll/{date}")
//    public List<Movie> getAllMovies(@PathVariable String date){
//        LocalDate date1 = LocalDate.parse(date);
//        return movieService.getAllMovies(date1);
//    }

    //Use this to get the seat details for a specific showtime
    @GetMapping("/get/{showTimeId}")
    public ShowTime getShowTime(@PathVariable Long showTimeId){
        return showTimeService.getShowTime(showTimeId);
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam String title){
        return movieService.searchMovies(title.toLowerCase());
    }

    @PostMapping("/{showTimeId}/createTicket")
    public Ticket createTicket(@RequestParam Long seatId, @RequestParam String userEmail){
        Seat seat = seatService.getSeat(seatId);
        User user = userService.getUserByEmailAddress(userEmail);
//        if(user == null){
//            user = userService.createUser(new User(userEmail));
//        }
        if(user == null || seat == null){
            return null;
        }
        //Check if the seat is available
        if(!seat.getIsReserved()){
            Ticket ticket = new Ticket(user, seat);
            //Create a ticket
            ticketService.createTicket(ticket);
            //Set the seat to unavailable
            seat.setIsReserved(true);
            seatService.updateSeat(seat);
            return ticket;
        }
        return null;
    }

//    @PostMapping("/create")
//    public Movie createMovie(@RequestParam String title, @RequestParam String genre, @RequestParam String releaseDate) {
//        Movie movie = new Movie();
//        movie.setTitle(title);
//        movie.setGenre(genre);
//        movie.setReleaseDate(LocalDate.parse(releaseDate));
//
//        Movie savedMovie = movieService.createMovie(movie);
//        Theater theater = theaterService.getTheater(1L);
//        // Generate and add showtimes in place
//        List<ShowTime> generatedShowTimes = showTimeService.generateShowTimes(savedMovie, theater);
//        for(ShowTime showTime : generatedShowTimes){
//            showTimeService.createShowTime(showTime);
//            List<Seat> seats = seatService.generateSeats(showTime);
//            for(Seat seat : seats){
//                seatService.createSeat(seat);
//            }
//            showTime.setSeats(seats);
//        }
//        savedMovie.setShowTimes(generatedShowTimes);
//
//        movieService.updateMovie(savedMovie);
//
//        return savedMovie;
//    }



}
