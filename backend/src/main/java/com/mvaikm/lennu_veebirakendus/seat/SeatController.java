package com.mvaikm.lennu_veebirakendus.seat;

import com.mvaikm.lennu_veebirakendus.pageresponse.PageResponse;
import jakarta.validation.Valid;
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

    @GetMapping
    public PageResponse<SeatDTO> getSeatsInFlight(
            @Valid @ModelAttribute SeatSearchCriteria criteria) {
        return seatService.findSeats(criteria);
    }

    @GetMapping("/{id}")
    public SeatDTO getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }
}
