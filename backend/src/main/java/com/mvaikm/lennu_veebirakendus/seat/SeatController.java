package com.mvaikm.lennu_veebirakendus.seat;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/register")
    public void registerSeat(@RequestBody SeatDTO seatDTO) {
        seatService.addSeat(seatDTO);
    }
}
