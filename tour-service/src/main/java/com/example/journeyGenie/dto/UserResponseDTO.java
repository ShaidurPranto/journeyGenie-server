package com.example.journeyGenie.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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

    @JsonManagedReference
    private List<TourResponseDTO> tours;
}

