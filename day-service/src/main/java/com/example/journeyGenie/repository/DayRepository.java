package com.example.journeyGenie.repository;

import com.example.journeyGenie.dto.DayResponseDTO;
import com.example.journeyGenie.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface DayRepository extends JpaRepository<Day, Long> {

    List<Day> findByTourId(Long tourId);
}
