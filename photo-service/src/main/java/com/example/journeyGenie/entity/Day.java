package com.example.journeyGenie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "days")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tourId;

    @Column(nullable = false)
    private String date;
}
