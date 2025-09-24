package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.DayResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("DAY-SERVICE")
public interface DayInterface {

    @GetMapping("/day/openfeign/{dayId}")
    DayResponseDTO getDayById(@PathVariable("dayId") Long dayId);

    @GetMapping("/day/openfeign/tour/{tourId}")
    List<DayResponseDTO> getDaysOfTour(@PathVariable("tourId") Long tourId);

    @PostMapping("/day/openfeign/create")
    void createDay(@RequestBody DayResponseDTO day);

    @DeleteMapping("/day/openfeign/delete/tour/{tourId}")
    void deleteDaysByTourId(@PathVariable("tourId") Long tourId);
}
