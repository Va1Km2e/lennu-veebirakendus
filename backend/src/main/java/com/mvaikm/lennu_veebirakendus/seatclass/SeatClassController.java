package com.mvaikm.lennu_veebirakendus.seatclass;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat-class")
@RequiredArgsConstructor
public class SeatClassController {
    private final SeatClassService seatClassService;

    @DeleteMapping("/{id}")
    public void deleteSeatClass(@PathVariable Long id) {
        seatClassService.deleteSeatClassById(id);
    }

    @PostMapping("/register")
    public void createSeatClass(@RequestBody SeatClassDTO seatClassDTO) {
        seatClassService.addSeatClass(seatClassDTO);
    }
}
