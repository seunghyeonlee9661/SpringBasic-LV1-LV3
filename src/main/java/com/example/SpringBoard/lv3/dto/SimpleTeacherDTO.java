package com.example.SpringBoard.lv3.dto;

import com.example.SpringBoard.lv3.entity.Teacher;
import lombok.Getter;

@Getter
public class SimpleTeacherDTO {
    private int id;
    private String name;
    private int year;
    private String company;
    private String phone;
    private String introduction;

    public SimpleTeacherDTO(Teacher teacher){
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.year = teacher.getYear();
        this.company = teacher.getCompany();
        this.phone = teacher.getPhone();
        this.introduction = teacher.getIntroduction();
    }
}
