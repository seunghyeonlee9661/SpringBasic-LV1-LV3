package com.example.SpringBoard.repository.backoffice;

import com.example.SpringBoard.entity.backoffice.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
