package com.example.journeyGenie.controller;

import com.example.journeyGenie.dto.TitleDTO;
import com.example.journeyGenie.dto.TourResponseDTO;
import com.example.journeyGenie.service.TourService;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @PostMapping("/title")
    public ResponseEntity<?> updateTitle(@RequestBody TitleDTO titleDTO, HttpServletRequest request) {
        Debug.log("Updating tour title: ");
        Debug.log("Tour ID: " + titleDTO.getTourid());
        Debug.log("New Title: " + titleDTO.getTitle());
        return tourService.updateTitle(titleDTO, request);
    }


    // the endpoints are for internal communications between microservices using feign

    @GetMapping("/openfeign/user/{userId}")
    List<TourResponseDTO> getToursOfUser(@PathVariable("userId") Long userId){
        return tourService.getToursOfUser(userId);
    }

    @GetMapping("/openfeign/{tourId}")
    TourResponseDTO getTourById(@PathVariable("tourId") Long tourId){
        return tourService.getTourById(tourId);
    }

}
