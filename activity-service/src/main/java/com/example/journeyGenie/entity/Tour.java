package com.example.journeyGenie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tour {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    private String startLocation;

    @Column(nullable = false)
    private String destination;

    private String budget;

    private String video;

    @Column(name = "blog", columnDefinition = "TEXT")   // ✅ TEXT in Postgres
    private String blog;
}
