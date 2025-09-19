package com.example.journeyGenie.controller;

import com.example.journeyGenie.dto.TitleDTO;
import com.example.journeyGenie.service.TourService;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
