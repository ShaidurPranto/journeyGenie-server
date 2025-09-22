package com.example.journeyGenie.dto;

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

    private Long dayId;
}
