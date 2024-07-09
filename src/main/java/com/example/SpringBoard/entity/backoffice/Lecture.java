package com.example.SpringBoard.entity.backoffice;

import com.example.SpringBoard.dto.backoffice.LectureRequestDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
/*
이노베이션 캠프 LV-3 : 관리자 Entity
 */

@Getter
@Entity
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, columnDefinition = "int")
    private int id;

    @Column(name="title", nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name="price", nullable = false, columnDefinition = "int")
    private int price;

    @Column(name="introduction", nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @Column(name="category", nullable = false, columnDefinition = "varchar(20)")
    private String category;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable = false)
    private Teacher teacher;

    @CreationTimestamp
    @Column(name="regist")
    private LocalDateTime regist;

    public Lecture(LectureRequestDTO requestDto, Teacher teacher) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.introduction = requestDto.getIntroduction();
        this.category = requestDto.getCategory();
        this.teacher = teacher;
    }

    public void update(LectureRequestDTO requestDto, Teacher teacher) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.introduction = requestDto.getIntroduction();
        this.category = requestDto.getCategory();
        this.teacher = teacher;
    }
}
