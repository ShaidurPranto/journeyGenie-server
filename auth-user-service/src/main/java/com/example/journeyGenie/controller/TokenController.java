package com.example.journeyGenie.controller;

import com.example.journeyGenie.dto.CouponRequestDTO;
import com.example.journeyGenie.service.TokenService;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    // Get user token balance
    @GetMapping("/balance")
    public ResponseEntity<?> getUserTokens(HttpServletRequest request, HttpServletResponse response) {
        Debug.log("Retrieving token balance for user");
        return tokenService.getUserToken(request, response);
    }

    // Deduct tokens (e.g., when user performs an action like generating a blog)
    @PostMapping("/deduct")
    public ResponseEntity<?> deductTokens(@RequestParam("tokens") int tokensToDeduct, HttpServletRequest request) {
        Debug.log("Deducting tokens for user");
        Debug.log("Tokens to Deduct: " + tokensToDeduct);
        return tokenService.deductTokens(request, tokensToDeduct);
    }

    // Add tokens (Normal purchase or coupon application)
    @PostMapping("/add")
    public ResponseEntity<?> addTokens(@RequestParam("tokens") int tokensToAdd, HttpServletRequest request) {
        Debug.log("Adding tokens for user");
        Debug.log("Tokens to Add: " + tokensToAdd);
        return tokenService.addTokens(request, tokensToAdd);
    }

    // Apply a coupon for token bonus (Received as JSON body)
    @PostMapping("/apply-coupon")
    public ResponseEntity<?> applyCoupon(@RequestBody CouponRequestDTO couponRequestDTO, HttpServletRequest request) {
        Debug.log("Applying coupon for user");
        Debug.log("Coupon Code received: " + couponRequestDTO.getCouponCode());

        if ("sizan".equalsIgnoreCase(couponRequestDTO.getCouponCode())) {
            Debug.log("Coupon 'sizan' applied, awarding 10 tokens");
            return tokenService.addTokens(request, 10);  // Award 10 tokens for the coupon "sizan"
        } else {
            Debug.log("Invalid coupon code entered: " + couponRequestDTO.getCouponCode());
            return ResponseEntity.badRequest().body("Invalid coupon code.");
        }
    }



    // the endpoints are for internal communications between microservices using feign

    @PostMapping("/openfeign/deductTokens")
    ResponseEntity<?> deductTokens(
            @RequestParam("userId") Long userId,
            @RequestParam("tokensToDeduct") int tokensToDeduct
    ){
        return tokenService.deductTokensByUserId(userId, tokensToDeduct);
    }

    @PostMapping("/openfeign/addTokens")
    ResponseEntity<?> addTokens(
            @RequestParam("userId") Long userId,
            @RequestParam("tokensToAdd") int tokensToAdd
    ){
        return tokenService.addTokensByUserId(userId, tokensToAdd);
    }

    @GetMapping("/token/openfeign/tokenCosts/video")
    int getVideoGenerationTokenCost(){
        return tokenService.getVideoGenerationTokenCost();
    }

    @GetMapping("/token/openfeign/tokenCosts/tourPerDay")
    int getTourGenerationTokenCostPerDay(){
        return tokenService.getTourGenerationTokenCostPerDay();
    }

    @GetMapping("/token/openfeign/tokenCosts/photoUpload")
    int getPhotoUploadCost(){
        return tokenService.getPhotoUploadTokenCost();
    }

    @GetMapping("/token/openfeign/tokenCosts/blog")
    int getBlogGenerationTokenCost(){
        return tokenService.getBlogGenerationTokenCost();
    }
}
