package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.ActivityResponseDTO;
import com.example.journeyGenie.dto.DayResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("ACTIVITY-SERVICE")
public interface ActivityInterface {
    @GetMapping("/activity/openfeign/{activityId}")
    ActivityResponseDTO getActivityById(@PathVariable("activityId") Long activityId);

    @GetMapping("/activity/openfeign/day/{dayId}")
    List<ActivityResponseDTO> getActivitiesOfDay(@PathVariable("dayId") Long dayId);

    @PostMapping("/activity/openfeign/create")
    void createActivity(@RequestBody ActivityResponseDTO activity);
}
