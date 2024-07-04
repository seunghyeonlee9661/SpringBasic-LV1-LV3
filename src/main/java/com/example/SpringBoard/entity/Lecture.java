package com.example.SpringBoard.entity;

import com.example.SpringBoard.DTO.LectureRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
/*
이노베이션 캠프 LV-3 : 관리자 Entity
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="lecture_id", nullable = false, columnDefinition = "int")
    private int lecture_id;

    @Column(name="name", nullable = false, columnDefinition = "varchar(100)")
    private String title;

    @Column(name="year", nullable = false, columnDefinition = "int")
    private int price;

    @Column(name="company", nullable = false, columnDefinition = "varchar(20)")
    private String introduction;

    @ManyToOne
    @JoinColumn(name="teacher_id", nullable = false)
    private Teacher teacher;

    @CreationTimestamp
    @Column(name="loanDate")
    private LocalDateTime regist;



    public Lecture() {
    }

    public Lecture(LectureRequestDTO requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.introduction = requestDto.getIntroduction();
        this.teacher = requestDto.getTeacher();
        this.regist = requestDto.getRegist();
    }
}
