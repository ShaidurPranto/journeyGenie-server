package com.example.journeyGenie.service;

import com.example.journeyGenie.auth.JWTService;
import com.example.journeyGenie.dto.*;
import com.example.journeyGenie.entity.Tour;
import com.example.journeyGenie.feign.DayInterface;
import com.example.journeyGenie.feign.UserInterface;
import com.example.journeyGenie.repository.TourRepository;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TourService {
    @Autowired
    private JWTService jwtService;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private DayInterface dayInterface;

    public ResponseEntity<?> createTour(TourResponseDTO tour, HttpServletRequest request) {
        saveTour(tour);
        return ResponseEntity.ok(userInterface.getUserByEmail(jwtService.getEmailFromRequest(request)));
    }

    public ResponseEntity<?> updateTitle(TitleDTO titleDTO, HttpServletRequest request) {
        UserResponseDTO existingUser = userInterface.getUserByEmail(jwtService.getEmailFromRequest(request));
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Tour tour = tourRepository.findById(titleDTO.getTourid())
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + titleDTO.getTourid()));

        if (!Objects.equals(tour.getUserId(), existingUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this tour");
        }

        tour.setTitle(titleDTO.getTitle());
        tourRepository.save(tour);
        return ResponseEntity.ok(existingUser);
    }

    public ResponseEntity<?> updateBlog(BlogDTO blogDto, HttpServletRequest request) {
        UserResponseDTO existingUser = userInterface.getUserByEmail(jwtService.getEmailFromRequest(request));
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Tour tour = tourRepository.findById(blogDto.getTourid())
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + blogDto.getTourid()));

        if (!Objects.equals(tour.getUserId(), existingUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this tour");
        }

        tour.setBlog(blogDto.getBlog());
        tourRepository.save(tour);
        return ResponseEntity.ok(existingUser);
    }

    // the endpoints are for internal communications between microservices using feign

    public TourResponseDTO getTourResponseFromId(Long tourId){
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + tourId));
        TourResponseDTO tourResponseDTO = new TourResponseDTO();
        tourResponseDTO.setId(tour.getId());
        tourResponseDTO.setUserId(tour.getUserId());
        tourResponseDTO.setTitle(tour.getTitle());
        tourResponseDTO.setStartDate(tour.getStartDate());
        tourResponseDTO.setEndDate(tour.getEndDate());
        tourResponseDTO.setStartLocation(tour.getStartLocation());
        tourResponseDTO.setDestination(tour.getDestination());
        tourResponseDTO.setBudget(tour.getBudget());
        tourResponseDTO.setVideo(tour.getVideo());
        tourResponseDTO.setBlog(tour.getBlog());
        List<DayResponseDTO> days = dayInterface.getDaysOfTour(tourId);
        tourResponseDTO.setDays(days);
        return tourResponseDTO;
    }

    public void saveTour(TourResponseDTO tour) {
        try{
            // save days of the tours using day interface
            for (DayResponseDTO day : tour.getDays()) {
                day.setTourId(tour.getId());
                dayInterface.createDay(day);
            }
            Debug.log("Days saved successfully for tour id: " + tour.getId());

            // save tour in database
            Tour newTour = new Tour();
            newTour.setUserId(tour.getUserId());
            newTour.setTitle(tour.getTitle());
            newTour.setStartDate(tour.getStartDate());
            newTour.setEndDate(tour.getEndDate());
            newTour.setStartLocation(tour.getStartLocation());
            newTour.setDestination(tour.getDestination());
            newTour.setBudget(tour.getBudget());
            newTour.setVideo(tour.getVideo());
            newTour.setBlog(tour.getBlog());
            Debug.log("Saving new tour: " + newTour);
            Tour savedTour = tourRepository.save(newTour);
            Debug.log("Tour saved successfully with id: " + savedTour.getId());
        }catch (Exception e) {
            Debug.log("❌ Failed to save days, tour not saved. Reason: " + e.getMessage());
        }
    }

    public List<TourResponseDTO> getToursOfUser(Long userId) {
        List<Tour> tours = tourRepository.findByUserId(userId);
        return tours.stream().map(tour -> {
            TourResponseDTO tourResponseDTO = new TourResponseDTO();
            tourResponseDTO.setId(tour.getId());
            tourResponseDTO.setUserId(userId);
            tourResponseDTO.setTitle(tour.getTitle());
            tourResponseDTO.setStartDate(tour.getStartDate());
            tourResponseDTO.setEndDate(tour.getEndDate());
            tourResponseDTO.setStartLocation(tour.getStartLocation());
            tourResponseDTO.setDestination(tour.getDestination());
            tourResponseDTO.setBudget(tour.getBudget());
            tourResponseDTO.setVideo(tour.getVideo());
            tourResponseDTO.setBlog(tour.getBlog());
            List<DayResponseDTO> days = dayInterface.getDaysOfTour(tour.getId());
            tourResponseDTO.setDays(days);
            return tourResponseDTO;
        }).toList();
    }

    public TourResponseDTO getTourById(Long tourId) {
        return getTourResponseFromId(tourId);
    }
}

