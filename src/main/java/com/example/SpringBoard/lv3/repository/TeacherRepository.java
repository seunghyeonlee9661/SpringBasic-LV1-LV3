package com.example.SpringBoard.lv3.repository;

import com.example.SpringBoard.lv3.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findAll();
    Optional<Teacher> findById(int teacher_id);
}
