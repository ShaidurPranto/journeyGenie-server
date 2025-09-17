package com.example.journeyGenie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
