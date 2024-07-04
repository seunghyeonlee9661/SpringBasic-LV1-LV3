package com.example.SpringBoard.repository;

import com.example.SpringBoard.entity.Admin;
import com.example.SpringBoard.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
