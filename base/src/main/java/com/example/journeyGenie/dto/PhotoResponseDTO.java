package com.example.journeyGenie.dto;

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

    private Long dayId;

    private String aiDescription;

    private LocalDateTime analyzedAt;

    private String analysisTags; // Comma-separated tags
}
