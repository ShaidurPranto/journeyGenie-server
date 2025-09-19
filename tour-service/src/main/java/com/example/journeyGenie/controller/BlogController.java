package com.example.journeyGenie.controller;

import com.example.journeyGenie.auth.JWTService;
import com.example.journeyGenie.dto.UserResponseDTO;
import com.example.journeyGenie.feign.UserInterface;
import com.example.journeyGenie.service.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserInterface userInterface;

    // POST /api/blog/generate/{tourId}
    @PostMapping("/generate/{tourId}")
    public ResponseEntity<?> generateBlog(@PathVariable("tourId") Long tourId,
                                          HttpServletRequest request) {
        // Check if the user has at least 5 tokens

        UserResponseDTO userResponseDTO = userInterface.getUserByEmail(jwtService.getEmailFromRequest(request));
        Integer userTokens = userResponseDTO.getToken();
        if (userTokens == null || userTokens < 5) {
            return ResponseEntity.status(400).body("Insufficient tokens. You need at least 5 tokens to generate a blog.");
        }

        // Proceed with generating the blog if the user has enough tokens
        ResponseEntity<?> blogResponse = blogService.generateAndSaveBlog(tourId, request);

        if (blogResponse.getStatusCode().is2xxSuccessful()) {
            userInterface.deductTokens(userResponseDTO.getId(),userInterface.getBlogGenerationTokenCost());
        }

        return blogResponse;
    }
}
