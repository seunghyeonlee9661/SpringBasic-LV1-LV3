package com.example.SpringBoard.DTO.backoffice;
import com.example.SpringBoard.entity.backoffice.Teacher;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeacherResponseDTO {
    private int id;
    private String name;
    private int year;
    private String company;
    private String phone;
    private String introduction;
    private List<LectureResponseDTO> lectures;

    public TeacherResponseDTO(Teacher teacher){
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.year = teacher.getYear();
        this.company = teacher.getCompany();
        this.phone = teacher.getPhone();
        this.introduction = teacher.getIntroduction();
        this.lectures = teacher.getLectures().stream().map(LectureResponseDTO::new).collect(Collectors.toList());
    }
}
