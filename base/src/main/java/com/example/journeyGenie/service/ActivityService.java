package com.example.journeyGenie.service;

import com.example.journeyGenie.authJWT.JWTService;
import com.example.journeyGenie.dto.ActivityDTO;
import com.example.journeyGenie.dto.DayResponseDTO;
import com.example.journeyGenie.dto.UserResponseDTO;
import com.example.journeyGenie.entity.Activity;
import com.example.journeyGenie.entity.Day;
import com.example.journeyGenie.entity.User;
import com.example.journeyGenie.feign.DayInterface;
import com.example.journeyGenie.feign.TourInterface;
import com.example.journeyGenie.feign.UserInterface;
import com.example.journeyGenie.repository.ActivityRepository;
import com.example.journeyGenie.repository.DayRepository;
import com.example.journeyGenie.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

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
}
