package com.example.journeyGenie.service;

import com.example.journeyGenie.dto.DayResponseDTO;
import com.example.journeyGenie.dto.TourResponseDTO;
import com.example.journeyGenie.entity.Day;
import com.example.journeyGenie.feign.ActivityInterface;
import com.example.journeyGenie.feign.PhotoInterface;
import com.example.journeyGenie.feign.TourInterface;
import com.example.journeyGenie.repository.DayRepository;
import com.example.journeyGenie.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private TourInterface tourInterface;

    @Autowired
    private ActivityInterface activityInterface;

    @Autowired
    private PhotoInterface photoInterface;

    public DayResponseDTO getDayById(Long dayId) {
        Day day = dayRepository.findById(dayId).orElse(null);
        if (day == null) {
            return null;
        }
        DayResponseDTO dayResponseDTO = new DayResponseDTO();
        dayResponseDTO.setId(day.getId());
        TourResponseDTO tourResponseDTO = tourInterface.getTourById(day.getTourId());
        dayResponseDTO.setTour(tourResponseDTO);
        dayResponseDTO.setDate(day.getDate());
        dayResponseDTO.setActivities(activityInterface.getActivitiesOfDay(day.getId()));
        dayResponseDTO.setPhotos(photoInterface.getPhotosOfDay(day.getId()));
        return dayResponseDTO;
    }

    public List<DayResponseDTO> getDaysOfTour(Long tourId) {
        List<DayResponseDTO> days = dayRepository.findByTourId(tourId).stream().map(day -> {
            DayResponseDTO dayResponseDTO = new DayResponseDTO();
            dayResponseDTO.setId(day.getId());
            TourResponseDTO tourResponseDTO = tourInterface.getTourById(day.getTourId());
            dayResponseDTO.setTour(tourResponseDTO);
            dayResponseDTO.setDate(day.getDate());
            dayResponseDTO.setActivities(activityInterface.getActivitiesOfDay(day.getId()));
            dayResponseDTO.setPhotos(photoInterface.getPhotosOfDay(day.getId()));
            return dayResponseDTO;
        }).toList();
        return days;
    }

    public void createDayFromResponse(DayResponseDTO day) {
        Day newDay = new Day();
        newDay.setId(day.getId());
        newDay.setTourId(day.getTour().getId());
        newDay.setDate(day.getDate());
        dayRepository.save(newDay);
        if (day.getActivities() != null) {
            day.getActivities().forEach(activityInterface::createActivity);
        }
        if (day.getPhotos() != null) {
            day.getPhotos().forEach(photoInterface::createPhoto);
        }
    }
}
