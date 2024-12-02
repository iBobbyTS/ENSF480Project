package edu.ucalgary.ensf480.group18.user.service;

import edu.ucalgary.ensf480.group18.user.model.Seat;
import edu.ucalgary.ensf480.group18.user.model.ShowTime;
import edu.ucalgary.ensf480.group18.user.repository.SeatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SeatServImpl implements SeatServ{
    @Autowired
    private SeatRepo seatRepo;

    @Override
    public Seat createSeat(Seat seat) {
        return seatRepo.save(seat);
    }

    @Override
    public Seat getSeat(Long seatId) {
        return seatRepo.findById(seatId).orElse(null);
    }

    @Override
    public List<Seat> getAllSeats(ShowTime showTime) {
        return seatRepo.findByShowTime(showTime);
    }

    @Override
    public List<Seat> generateSeats(ShowTime showTime) {
        List<Seat> seats = new ArrayList<>();
        int rows = 10;
        int columns = 10;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                Seat seat = new Seat(i, j, 10, false, showTime);
                seat.setShowTime(showTime);
                seats.add(seat);

            }
        }

        return seats;
    }

    @Override
    public void updateSeat(Seat seat) {
        seatRepo.save(seat);
    }

//    @Override
//    public Seat updateSeat(Long seatId, Seat seat) {
//        Seat seatToUpdate = seatRepo.findById(seatId).orElse(null);
//        if (seatToUpdate != null) {
//            seatToUpdate.setSeatNumber(seat.getSeatNumber());
//            seatToUpdate.setShowTime(seat.getShowTime());
//            seatToUpdate.setSeatStatus(seat.getSeatStatus());
//            return seatRepo.save(seatToUpdate);
//        }
//        return null;
//    }
}
