package com.example.journeyGenie.controller;

import com.example.journeyGenie.dto.UserResponseDTO;
import com.example.journeyGenie.entity.User;
import com.example.journeyGenie.service.TokenService;
import com.example.journeyGenie.service.UserService;
import com.example.journeyGenie.util.Debug;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        Debug.log("User signup request: ");
        Debug.log("Name: " + user.getName());
        Debug.log("Email: " + user.getEmail());
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user , HttpServletResponse response) {
        Debug.log("User login request: ");
        Debug.log("Name: " + user.getName());
        Debug.log("Email: " + user.getEmail());
        return userService.loginUser(user,response);
    }

    // endpoint to logout user
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Debug.log("User logout request");
        return userService.logoutUser(request,response);
    }



    // the endpoints are for internal communications between microservices using feign

    @GetMapping("/openfeign/id")
    UserResponseDTO getUserById(@RequestParam("userId") Long userId){
        return userService.getUserResponseById(userId);
    }

    @GetMapping("/openfeign/email")
    UserResponseDTO getUserByEmail(@RequestParam("email") String email){
        return userService.getUserResponseByEmail(email);
    }
}
