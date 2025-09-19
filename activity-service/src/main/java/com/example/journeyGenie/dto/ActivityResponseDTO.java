package com.example.journeyGenie.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityResponseDTO {
    private Long id;

    private String description;

    private String status;

    @JsonBackReference
    private DayResponseDTO day;
}
