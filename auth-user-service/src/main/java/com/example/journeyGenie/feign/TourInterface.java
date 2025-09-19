package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.TourResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("TOUR-SERVICE")
public interface TourInterface {

    @GetMapping("/tours/user/{userId}")
    List<TourResponseDTO> getToursOfUser(@PathVariable("userId") Long userId);

    @GetMapping("/tour/{tourId}")
    TourResponseDTO getTourById(@PathVariable("tourId") Long tourId);
}
