package com.mvaikm.lennu_veebirakendus.seatclass;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seat-class")
public class SeatClassController {
    private final SeatClassService seatClassService;

    public SeatClassController( SeatClassService seatClassService) {
        this.seatClassService = seatClassService;
    }


    @DeleteMapping("/{id}")
    public void deleteSeatClass(@PathVariable Long id) {
        seatClassService.deleteSeatClassById(id);
    }

    @PostMapping("/register")
    public void createSeatClass(@RequestBody SeatClassDTO seatClassDTO) {
        seatClassService.addSeatClass(seatClassDTO);
    }
}
