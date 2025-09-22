package com.example.journeyGenie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayDTO {
    private String date;
    private String title;           // from prompt, not stored in entity
    private String transportation;  // from prompt, not stored in entity
    private String hotel;           // from prompt, not stored in entity
    private List<ActivityDTO> activities;
}
