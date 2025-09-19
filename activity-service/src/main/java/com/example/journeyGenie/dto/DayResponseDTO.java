package com.example.journeyGenie.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DayResponseDTO {
    private Long id;

    @JsonBackReference
    private TourResponseDTO tour;

    private String date;

    @JsonManagedReference
    private List<ActivityResponseDTO> activities;

    @JsonManagedReference
    private List<PhotoResponseDTO> photos;
}
