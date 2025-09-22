package com.example.journeyGenie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanRequestDTO {
    private String destination;
    private String startDate;
    private String endDate;
    private String budget;
}
