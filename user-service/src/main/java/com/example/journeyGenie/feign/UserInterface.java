package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserInterface {

    @GetMapping("/user/id")
    UserResponseDTO getUserById(@RequestParam("userId") Long userId);

    @GetMapping("/user/email")
    UserResponseDTO getUserByEmail(@RequestParam("email") String email);

    @PostMapping("/user/deductTokens")
    ResponseEntity<?> deductTokens(
            @RequestParam("userId") Long userId,
            @RequestParam("tokensToDeduct") int tokensToDeduct
    );

    @GetMapping("/user/tokenCosts/video")
    int getVideoGenerationTokenCost();

    @GetMapping("/user/tokenCosts/tourPerDay")
    int getTourGenerationTokenCostPerDay();

    @GetMapping("/user/tokenCosts/photoUpload")
    int getPhotoUploadCost();

    @GetMapping("/user/tokenCosts/blog")
    int getBlogGenerationTokenCost();

    @PostMapping("/user/addTokens")
    ResponseEntity<?> addTokens(
            @RequestParam("userId") Long userId,
            @RequestParam("tokensToAdd") int tokensToAdd
    );
}
