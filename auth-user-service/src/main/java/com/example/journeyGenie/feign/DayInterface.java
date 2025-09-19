package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.DayResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("DAY-SERVICE")
public interface DayInterface {

    @GetMapping("/day/{dayId}")
    DayResponseDTO getDayById(@PathVariable("dayId") Long dayId);

    @GetMapping("/days/{tourId}")
    List<DayResponseDTO> getDaysOfTour(@PathVariable("tourId") Long tourId);

    @PostMapping("/day")
    void createDay(@RequestBody DayResponseDTO day);
}
