package com.drsimple.jwtsecurity.ticketbooking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessimisticSeatBookingTestService {

    private final MovieTicketBookingService movieTicketBookingService;

    public PessimisticSeatBookingTestService(MovieTicketBookingService movieTicketBookingService) {
        this.movieTicketBookingService = movieTicketBookingService;
    }

    public void testPessimisticLocking(Long seatId) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            try {
                movieTicketBookingService.bookSeatWithPessimistic(seatId);
            } catch (RuntimeException e) {
                System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                movieTicketBookingService.bookSeatWithPessimistic(seatId);
            } catch (RuntimeException e) {
                System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}