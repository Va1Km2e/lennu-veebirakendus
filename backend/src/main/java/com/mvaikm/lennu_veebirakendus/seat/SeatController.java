package com.mvaikm.lennu_veebirakendus.seat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @PostMapping("/register")
    public void registerSeat(@RequestBody SeatDTO seatDTO) {
        seatService.addSeat(seatDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
    }
}
