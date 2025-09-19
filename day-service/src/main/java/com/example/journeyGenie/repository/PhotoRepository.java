package com.example.journeyGenie.repository;

import com.example.journeyGenie.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByDayId(Long dayId);
}
