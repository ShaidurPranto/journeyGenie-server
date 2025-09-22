package com.example.journeyGenie.service;

import com.example.journeyGenie.auth.JWTService;
import com.example.journeyGenie.dto.ActivityDTO;
import com.example.journeyGenie.dto.ActivityResponseDTO;
import com.example.journeyGenie.dto.DayResponseDTO;
import com.example.journeyGenie.dto.UserResponseDTO;
import com.example.journeyGenie.entity.Activity;
import com.example.journeyGenie.feign.DayInterface;
import com.example.journeyGenie.feign.TourInterface;
import com.example.journeyGenie.feign.UserInterface;
import com.example.journeyGenie.repository.ActivityRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private DayInterface dayInterface;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private TourInterface tourInterface;

    public ResponseEntity<?> addActivity(ActivityDTO activity, HttpServletRequest request) {
        // Create a new Activity entity from the DTO
        Activity newActivity = new Activity();
        newActivity.setDescription(activity.getDescription());
        newActivity.setStatus("pending");
        newActivity.setDayId(activity.getDayid());

        activityRepository.save(newActivity);
        UserResponseDTO existingUser = userInterface.getUserByEmail(jwtService.getEmailFromRequest(request));
        return ResponseEntity.ok(existingUser);
    }


    @Transactional
    public ResponseEntity<?> completeByClone(Long activityId, HttpServletRequest request) {
        Activity old = activityRepository.findById(activityId).orElse(null);
        if (old == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activity not found");

        // clone as done
        Activity cloned = new Activity();
        cloned.setDescription(old.getDescription());
        cloned.setStatus("done");
        cloned.setDayId(old.getDayId());
        activityRepository.save(cloned);

        // delete original
        activityRepository.delete(old);

        // return a fresh user (so frontend gets the new activity id/state)
        UserResponseDTO updated = userInterface.getUserByEmail(jwtService.getEmailFromRequest(request));
        return ResponseEntity.ok(updated);
    }

    public ActivityResponseDTO getActivityById(Long activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null) {
            return null; // or throw an exception
        }
        ActivityResponseDTO dto = new ActivityResponseDTO();
        dto.setId(activity.getId());
        dto.setDescription(activity.getDescription());
        dto.setStatus(activity.getStatus());
        dto.setDayId(activity.getDayId());
        return dto;
    }

    public List<ActivityResponseDTO> getActivitiesOfDay(Long dayId) {
        List<Activity> activities = activityRepository.findByDayId(dayId);
        return activities.stream().map(activity -> {
            ActivityResponseDTO dto = new ActivityResponseDTO();
            dto.setId(activity.getId());
            dto.setDescription(activity.getDescription());
            dto.setStatus(activity.getStatus());
            dto.setDayId(activity.getDayId());
            return dto;
        }).toList();
    }

    public void createActivity(ActivityResponseDTO activity) {
        Activity newActivity = new Activity();
        newActivity.setDescription(activity.getDescription());
        newActivity.setStatus(activity.getStatus());
        if (activity.getDayId() != null) {
            newActivity.setDayId(activity.getDayId());
        }
        activityRepository.save(newActivity);
    }
}
