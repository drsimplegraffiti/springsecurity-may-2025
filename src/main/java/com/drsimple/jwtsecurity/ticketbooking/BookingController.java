package com.drsimple.jwtsecurity.ticketbooking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final OptimisticSeatBookingTestService optimisticSeatBookingTestService;
    private final PessimisticSeatBookingTestService pessimisticSeatBookingTestService;

    public BookingController(OptimisticSeatBookingTestService optimisticSeatBookingTestService, PessimisticSeatBookingTestService pessimisticSeatBookingTestService) {
        this.optimisticSeatBookingTestService = optimisticSeatBookingTestService;
        this.pessimisticSeatBookingTestService = pessimisticSeatBookingTestService;
    }


    @GetMapping("/optimistic/{seatId}")
    public String testOptimistic(@PathVariable Long seatId) throws InterruptedException {
        optimisticSeatBookingTestService.testOptimisticLocking(seatId); // optimistic locking, this will check for the version number
        return "Optimistic locking test started! Check logs for results.";
    }

    @GetMapping("/pessimistic/{seatId}")
    public String testPessimistic(@PathVariable Long seatId) throws InterruptedException {
        pessimisticSeatBookingTestService.testPessimisticLocking(seatId); // this will apply the lock on the table
        return "Pessimistic locking test started! Check logs for results.";
    }
}