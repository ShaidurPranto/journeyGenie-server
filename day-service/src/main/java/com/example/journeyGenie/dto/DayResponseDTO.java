package com.example.journeyGenie.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DayResponseDTO {
    private Long id;

    private Long tourId;

    private String date;

    private List<ActivityResponseDTO> activities;

    private List<PhotoResponseDTO> photos;
}
