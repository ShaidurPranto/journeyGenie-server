package com.example.journeyGenie.controller;

import com.example.journeyGenie.dto.ActivityDTO;
import com.example.journeyGenie.dto.ActivityResponseDTO;
import com.example.journeyGenie.service.ActivityService;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/add")
    public ResponseEntity<?> addActivity(@RequestBody ActivityDTO activity, HttpServletRequest request) {
        Debug.log("Creating activity: ");
        Debug.log("Activity description: " + activity.getDescription());
        Debug.log("Day id: " + activity.getDayid());
        return activityService.addActivity(activity, request);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeByClone(@PathVariable("id") Long id, HttpServletRequest request) {
        return activityService.completeByClone(id, request);
    }


    // all the endpoints are for internal communications between microservices using feign

    @GetMapping("/openfeign/{activityId}")
    ActivityResponseDTO getActivityById(@PathVariable("activityId") Long activityId){
        return activityService.getActivityById(activityId);
    }

    @GetMapping("/openfeign/day/{dayId}")
    List<ActivityResponseDTO> getActivitiesOfDay(@PathVariable("dayId") Long dayId){
        return activityService.getActivitiesOfDay(dayId);
    }

    @PostMapping("/openfeign/create")
    void createActivity(@RequestBody ActivityResponseDTO activity){
        activityService.createActivity(activity);
    }
}
