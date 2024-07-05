package com.example.SpringBoard.repository.backoffice;

import com.example.SpringBoard.entity.backoffice.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
}
