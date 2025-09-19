package com.example.journeyGenie.feign;

import com.example.journeyGenie.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserInterface {

    @GetMapping("/user/openfeign/id")
    UserResponseDTO getUserById(@RequestParam("userId") Long userId);

    @GetMapping("/user/openfeign/email")
    UserResponseDTO getUserByEmail(@RequestParam("email") String email);

    @PostMapping("/token/openfeign/deductTokens")
    ResponseEntity<?> deductTokens(
            @RequestParam("userId") Long userId,
            @RequestParam("tokensToDeduct") int tokensToDeduct
    );

    @PostMapping("/token/openfeign/addTokens")
    ResponseEntity<?> addTokens(
            @RequestParam("userId") Long userId,
            @RequestParam("tokensToAdd") int tokensToAdd
    );

    @GetMapping("/token/openfeign/tokenCosts/video")
    int getVideoGenerationTokenCost();

    @GetMapping("/token/openfeign/tokenCosts/tourPerDay")
    int getTourGenerationTokenCostPerDay();

    @GetMapping("/token/openfeign/tokenCosts/photoUpload")
    int getPhotoUploadCost();

    @GetMapping("/token/openfeign/tokenCosts/blog")
    int getBlogGenerationTokenCost();
}
