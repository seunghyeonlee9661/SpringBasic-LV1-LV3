package com.example.SpringBoard.DTO;
import com.example.SpringBoard.entity.Lecture;
import com.example.SpringBoard.entity.Teacher;
import lombok.Getter;

@Getter
public class TeacherResponseDTO {
    private int teacher_id;
    private String name;
    private int year;
    private String company;
    private String phone;
    private String introduction;

    public TeacherResponseDTO(Teacher teacher){
        this.teacher_id = teacher.getTeacher_id();
        this.name = teacher.getName();
        this.year = teacher.getYear();
        this.company = teacher.getCompany();
        this.phone = teacher.getPhone();
        this.introduction = teacher.getIntroduction();
    }

    public TeacherResponseDTO(int teacher_id, String name, int year, String company, String phone, String introduction) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.year = year;
        this.company = company;
        this.phone = phone;
        this.introduction = introduction;
    }
}
