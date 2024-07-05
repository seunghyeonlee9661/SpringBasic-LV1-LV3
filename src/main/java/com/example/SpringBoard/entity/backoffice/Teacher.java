package com.example.SpringBoard.entity.backoffice;

import com.example.SpringBoard.DTO.backoffice.TeacherRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/*
이노베이션 캠프 LV-3 : 관리자 Entity
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, columnDefinition = "int")
    private int id;

    @Column(name="name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name="year", nullable = false, columnDefinition = "int")
    private int year;

    @Column(name="company", nullable = false, columnDefinition = "varchar(20)")
    private String company;

    @Column(name="phone", nullable = false, columnDefinition = "varchar(20)")
    private String phone;

    @Column(name="introduction", nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @OneToMany(mappedBy = "teacher", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Lecture> lectures = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(TeacherRequestDTO requestDto) {
        this.name = requestDto.getName();
        this.year = requestDto.getYear();
        this.company = requestDto.getCompany();
        this.phone = requestDto.getPhone();
        this.introduction = requestDto.getIntroduction();
    }
}
