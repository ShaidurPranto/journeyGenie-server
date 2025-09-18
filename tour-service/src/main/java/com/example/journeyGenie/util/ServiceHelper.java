package com.example.journeyGenie.util;

import com.example.journeyGenie.dto.TourResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceHelper {
    public TourResponseDTO getTourById(List<TourResponseDTO> tours, Long tourId) {
        for (TourResponseDTO tour : tours) {
            if (tour.getId().equals(tourId)) {
                return tour;
            }
        }
        return null;
    }
}
