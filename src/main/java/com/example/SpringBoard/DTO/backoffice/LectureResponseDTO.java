package com.example.SpringBoard.DTO.backoffice;
import com.example.SpringBoard.entity.backoffice.Lecture;
import com.example.SpringBoard.entity.backoffice.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureResponseDTO {
    private int id;
    private String title;
    private int price;
    private String introduction;
    private String category;
    private Teacher teacher;
    private LocalDateTime regist;

    public LectureResponseDTO(Lecture backofficeLecture){
        this.id = backofficeLecture.getId();
        this.title = backofficeLecture.getTitle();
        this.price = backofficeLecture.getPrice();
        this.introduction = backofficeLecture.getIntroduction();
        this.category = backofficeLecture.getCategory();
        this.teacher = backofficeLecture.getTeacher();
        this.regist = backofficeLecture.getRegist();
    }
    public LectureResponseDTO(int id, String title, int price, String introduction,String category, Teacher teacher, LocalDateTime regist) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.introduction = introduction;
        this.category = category;
        this.teacher = teacher;
        this.regist = regist;
    }
}
