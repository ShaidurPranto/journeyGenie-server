package com.example.journeyGenie.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String password;

    private Integer token;

    private List<TourResponseDTO> tours;
}

