package com.example.journeyGenie.feign;


import com.example.journeyGenie.dto.ActivityResponseDTO;
import com.example.journeyGenie.dto.PhotoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("PHOTO-SERVICE")
public interface PhotoInterface {
    @GetMapping("/photo/openfeign/{photoId}")
    PhotoResponseDTO getPhotoById(@PathVariable("photoId") Long photoId);

    @GetMapping("/photo/openfeign/day/{dayId}")
    List<PhotoResponseDTO> getPhotosOfDay(@PathVariable("dayId") Long dayId);

    @PostMapping("/photo/openfeign/create")
    void createPhoto(@RequestBody PhotoResponseDTO photo);
}
