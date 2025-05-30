package com.drsimple.jwtsecurity.ticketbooking;

import com.drsimple.jwtsecurity.exception.CustomBadRequestException;
import com.drsimple.jwtsecurity.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }




    @Transactional
    public void bookSeatWithPessimistic(Long seatId) {

        System.out.println(Thread.currentThread().getName() + " is attempting to fetch the seat");

        //fetch the seat with Pessimistic lock
        Seat seat = seatRepository.findByIdAndLock(seatId);

        System.out.println(Thread.currentThread().getName() + " acquired the lock for seat id " + seatId);

        if (seat.isBooked()) {
            System.out.println(Thread.currentThread().getName() + " failed Seat Id " + seatId + " is already booked ");
            throw new RuntimeException("Seat already booked !");
        }
        //booking seat
        System.out.println(Thread.currentThread().getName() + " booking the seat " + seatId);

        seat.setBooked(true);
        //version check will occurs here
        seatRepository.save(seat);
        System.out.println(Thread.currentThread().getName() + " successfully book the seat with ID " + seatId);
    }

    @Transactional
    public Seat bookSeat(Long seatId) {
      //fetch existing seat
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new NotFoundException("Seat not found with id: " + seatId));

        System.out.println(Thread.currentThread().getName() + " - Booking seat: " + seat.getId() +  " " + seat.getVersion());

        //check if already booked
        if (seat.isBooked()) {
            throw new CustomBadRequestException("Seat already booked");
        }

        //book the seat
        seat.setBooked(true);

        //save the updated seat and version check will be done by JPA
        return seatRepository.save(seat);
    }
}
