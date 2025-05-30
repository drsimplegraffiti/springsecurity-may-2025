package com.drsimple.jwtsecurity.ticketbooking;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptimisticSeatBookingTestService {

    private final MovieTicketBookingService movieTicketBookingService;

    public OptimisticSeatBookingTestService(MovieTicketBookingService movieTicketBookingService) {
        this.movieTicketBookingService = movieTicketBookingService;
    }


    @PostConstruct
    public void init() {
        for (long i = 1; i <= 3; i++) {
            if (!movieTicketBookingService.existsById(i)) {
                Seat seat = new Seat();
                seat.setBooked(false);
                seat.setMovieName("A" + i);
                movieTicketBookingService.saveSeat(seat); // this will persist with auto-generated ID and version
            }
        }
    }


    public void testOptimisticLocking(Long seatId) throws InterruptedException {
        Thread th1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " is attempting to book the seat");
                Seat seat = movieTicketBookingService.bookSeat(seatId);
                System.out.println(Thread.currentThread().getName() + " successfully booked the seat with version " + seat.getVersion());
            } catch (Exception ex) {
                System.out.println(Thread.currentThread().getName() + " failed : " + ex.getMessage());
            }
        });

        Thread th2 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " is attempting to book the seat");
                Seat seat = movieTicketBookingService.bookSeat(seatId);
                System.out.println(Thread.currentThread().getName() + " successfully booked the seat with version " + seat.getVersion());
            } catch (Exception ex) {
                System.out.println(Thread.currentThread().getName() + " failed : " + ex.getMessage());
            }
        });

        th1.start();
        th2.start();
        th1.join(); // ensure th1 completes before proceeding
        th2.join(); // runs after th1 completes
    }
}