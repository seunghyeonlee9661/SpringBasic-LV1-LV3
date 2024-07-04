package com.example.SpringBoard.DTO;
import com.example.SpringBoard.entity.Teacher;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
public class LectureRequestDTO {
    private String title;
    private int price;
    private String introduction;
    private Teacher teacher;
    private LocalDateTime regist;
}
