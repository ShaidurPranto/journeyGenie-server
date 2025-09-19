package com.example.journeyGenie.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ACTIVITY-SERVICE")
public interface ActivityInterface {
}
