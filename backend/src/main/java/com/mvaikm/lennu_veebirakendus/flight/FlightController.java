package com.mvaikm.lennu_veebirakendus.flight;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;


    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlightById(id);
    }

    @PostMapping("/register")
    public void createFlight(@RequestBody FlightDTO flightDTO) {
        flightService.addFlight(flightDTO);
    }
}
