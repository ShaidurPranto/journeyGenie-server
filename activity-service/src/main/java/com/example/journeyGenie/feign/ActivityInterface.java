package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.ActivityResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ACTIVITY-SERVICE")
public interface ActivityInterface {
    @GetMapping("/activity/openfeign/{activityId}")
    ActivityResponseDTO getActivityById(@PathVariable("activityId") Long activityId);

    @GetMapping("/activity/openfeign/day/{dayId}")
    List<ActivityResponseDTO> getActivitiesOfDay(@PathVariable("dayId") Long dayId);

    @PostMapping("/activity/openfeign/create")
    void createActivity(@RequestBody ActivityResponseDTO activity);

    @DeleteMapping("/activity/openfeign/delete/day/{dayId}")
    void deleteActivitiesByDayId(@PathVariable("dayId") Long dayId);
}
