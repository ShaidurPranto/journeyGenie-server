package com.example.journeyGenie.controller;

import com.example.journeyGenie.authJWT.JWTService;
import com.example.journeyGenie.dto.UserResponseDTO;
import com.example.journeyGenie.feign.UserInterface;
import com.example.journeyGenie.service.PhotoService;
import com.example.journeyGenie.service.TokenService;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserInterface userInterface;

    @PostMapping(value = "/upload")  // Remove the consumes constraint
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam("dayid") Long dayid,
                                    HttpServletRequest request) {

        // Check if the user has at least 10 tokens for photo upload
        UserResponseDTO userResponseDTO = userInterface.getUserByEmail(jwtService.getEmailFromRequest(request));
        Integer userTokens = userResponseDTO.getToken();

        // Log the token value after extraction
        System.out.println("User Tokens: " + userTokens);

        if (userTokens == null || userTokens < 1) {
            return ResponseEntity.status(400).body("Insufficient tokens. You need at least 1 tokens to upload a photo.");
        }

        // Deduct 1 tokens for photo upload
        ResponseEntity<?> deductionResponse = userInterface.deductTokens(userResponseDTO.getId() ,userInterface.getPhotoUploadCost()); // Deduct 1 tokens for upload
        if (!deductionResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(400).body("Failed to deduct tokens.");
        }

        // Proceed with photo upload
        Debug.log("=== PHOTO UPLOAD REQUEST ===");
        Debug.log("Day ID: " + dayid);
        Debug.log("File name: " + (file != null ? file.getOriginalFilename() : "null"));
        Debug.log("File size: " + (file != null ? file.getSize() + " bytes" : "null"));
        Debug.log("Content type: " + (file != null ? file.getContentType() : "null"));

        return photoService.upload(file, dayid, request);
    }
}
