package com.example.journeyGenie.feign;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("PHOTO-SERVICE")
public interface PhotoInterface {
}
