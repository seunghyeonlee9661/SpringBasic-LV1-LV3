package com.example.SpringBoard.DTO.backoffice;
import com.example.SpringBoard.entity.backoffice.Teacher;
import lombok.Getter;

@Getter
public class TeacherResponseDTO {
    private int id;
    private String name;
    private int year;
    private String company;
    private String phone;
    private String introduction;

    public TeacherResponseDTO(Teacher backofficeTeacher){
        this.id = backofficeTeacher.getId();
        this.name = backofficeTeacher.getName();
        this.year = backofficeTeacher.getYear();
        this.company = backofficeTeacher.getCompany();
        this.phone = backofficeTeacher.getPhone();
        this.introduction = backofficeTeacher.getIntroduction();
    }

    public TeacherResponseDTO(int id, String name, int year, String company, String phone, String introduction) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.company = company;
        this.phone = phone;
        this.introduction = introduction;
    }
}
