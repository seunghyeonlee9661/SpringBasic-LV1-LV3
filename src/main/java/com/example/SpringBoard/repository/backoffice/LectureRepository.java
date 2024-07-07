package com.example.SpringBoard.repository.backoffice;

import com.example.SpringBoard.entity.backoffice.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    Page<Lecture> findByCategoryOrderByRegistDesc(String category,Pageable pageable);
}