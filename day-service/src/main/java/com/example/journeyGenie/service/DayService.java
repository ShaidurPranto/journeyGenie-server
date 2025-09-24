package com.example.journeyGenie.service;

import com.example.journeyGenie.dto.DayResponseDTO;
import com.example.journeyGenie.dto.TourResponseDTO;
import com.example.journeyGenie.entity.Day;
import com.example.journeyGenie.feign.ActivityInterface;
import com.example.journeyGenie.feign.PhotoInterface;
import com.example.journeyGenie.feign.TourInterface;
import com.example.journeyGenie.repository.DayRepository;
import com.example.journeyGenie.util.Debug;
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
        dayResponseDTO.setTourId(day.getTourId());
        dayResponseDTO.setDate(day.getDate());
        dayResponseDTO.setActivities(activityInterface.getActivitiesOfDay(day.getId()));
        dayResponseDTO.setPhotos(photoInterface.getPhotosOfDay(day.getId()));
        return dayResponseDTO;
    }

    public List<DayResponseDTO> getDaysOfTour(Long tourId) {
        return dayRepository.findByTourId(tourId).stream().map(day -> {
            DayResponseDTO dayResponseDTO = new DayResponseDTO();
            dayResponseDTO.setId(day.getId());
            dayResponseDTO.setTourId(day.getTourId());
            dayResponseDTO.setDate(day.getDate());
            dayResponseDTO.setActivities(activityInterface.getActivitiesOfDay(day.getId()));
            dayResponseDTO.setPhotos(photoInterface.getPhotosOfDay(day.getId()));
            return dayResponseDTO;
        }).toList();
    }

    public void createDayFromResponse(DayResponseDTO day) {
        Day createdDay = null;
        try{
            // creating day
            Day newDay = new Day();
            newDay.setId(day.getId());
            newDay.setTourId(day.getTourId());
            newDay.setDate(day.getDate());
            Debug.log("Creating day: " + newDay);
            createdDay = dayRepository.save(newDay);
            Debug.log("Day created successfully with id: " + createdDay.getId());

            // creating activities
            if (day.getActivities() != null) {
                Day finalCreatedDay = createdDay;
                day.getActivities().forEach(activity -> activity.setDayId(finalCreatedDay.getId()));
                day.getActivities().forEach(activityInterface::createActivity);
            }
            Debug.log("Activities created for day id: " + createdDay.getId());

            // creating photos
            if (day.getPhotos() != null) {
                Day finalCreatedDay1 = createdDay;
                day.getPhotos().forEach(photo -> photo.setDayId(finalCreatedDay1.getId()));
                day.getPhotos().forEach(photoInterface::createPhoto);
            }
            Debug.log("Photos created for day id: " + createdDay.getId());
        }catch (Exception e){
            Debug.log("❌ Failed to create day. Reason: " + e.getMessage());

            if(createdDay != null){
                // rollback day
                Debug.log("Rolling back day with id: " + createdDay.getId());
                dayRepository.deleteById(createdDay.getId());
                Debug.log("Rolled back day with id: " + createdDay.getId());

                // rollback activities
                Debug.log("Rolling back activities of day with id: " + createdDay.getId());
                activityInterface.deleteActivitiesByDayId(createdDay.getId());
                Debug.log("Rolled back activities of day with id: " + createdDay.getId());

                // rollback photos
                Debug.log("Rolling back photos of day with id: " + createdDay.getId());
                photoInterface.deletePhotosByDayId(createdDay.getId());
                Debug.log("Rolled back photos of day with id: " + createdDay.getId());
            }else {
                Debug.log("No day to rollback");
            }
        }
    }

    public void deleteDaysByTourId(Long tourId) {
        List<Day> days = dayRepository.findByTourId(tourId);
        for (Day day : days) {
            // delete activities of the day
            activityInterface.deleteActivitiesByDayId(day.getId());
            Debug.log("Deleted activities of day id: " + day.getId());

            // delete photos of the day
            photoInterface.deletePhotosByDayId(day.getId());
            Debug.log("Deleted photos of day id: " + day.getId());

            // delete the day
            dayRepository.deleteById(day.getId());
            Debug.log("Deleted day id: " + day.getId());
        }
    }
}
