package com.example.journeyGenie.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhotoResponseDTO {
    private Long id;

    private String link;

    @JsonBackReference
    private DayResponseDTO day;

    private String aiDescription;

    private LocalDateTime analyzedAt;

    private String analysisTags; // Comma-separated tags
}
