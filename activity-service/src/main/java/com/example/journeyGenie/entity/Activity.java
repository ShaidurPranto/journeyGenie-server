package com.example.journeyGenie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dayid", nullable = false)
    private Long dayId;

    private String description;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
    private String status = "pending";
}
