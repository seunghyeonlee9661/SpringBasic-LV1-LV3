package com.example.SpringBoard.DTO;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class TeacherRequestDTO {
    private int teacher_id;
    private String name;
    private int year;
    private String company;
    private String phone;
    private String introduction;
}
