package com.example.journeyGenie.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TourResponseDTO {
    private Long id;

    private Long userId;

    private String title;

    private String startDate;

    private String endDate;

    private String startLocation;

    private String destination;

    private String budget;

    private String video;

    private String blog;

    private List<DayResponseDTO> days;
}
