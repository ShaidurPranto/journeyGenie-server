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
public class TourResponseDTO {
    private Long id;

    @JsonBackReference
    private UserResponseDTO user;

    private String title;

    private String startDate;

    private String endDate;

    private String startLocation;

    private String destination;

    private String budget;

    private String video;

    private String blog;

    @JsonManagedReference
    private List<DayResponseDTO> days;
}
