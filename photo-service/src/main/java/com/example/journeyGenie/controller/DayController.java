package com.example.journeyGenie.controller;

import com.example.journeyGenie.dto.DayResponseDTO;
import com.example.journeyGenie.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day")
public class DayController {

    @Autowired
    private DayService dayService;

    @GetMapping("/day/openfeign/{dayId}")
    DayResponseDTO getDayById(@PathVariable("dayId") Long dayId){
        return dayService.getDayById(dayId);
    }

    @GetMapping("/day/openfeign/tour/{tourId}")
    List<DayResponseDTO> getDaysOfTour(@PathVariable("tourId") Long tourId){
        return dayService.getDaysOfTour(tourId);
    }

    @PostMapping("/day/openfeign/create")
    void createDay(@RequestBody DayResponseDTO day){
        dayService.createDayFromResponse(day);
    }
}
