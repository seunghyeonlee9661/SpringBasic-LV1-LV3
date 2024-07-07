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
    private String teachername;
    private LocalDateTime regist;

    public LectureResponseDTO(Lecture lecture){
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.price = lecture.getPrice();
        this.introduction = lecture.getIntroduction();
        this.category = lecture.getCategory();
        this.teachername = lecture.getTeacher().getName();
        this.regist = lecture.getRegist();
    }
}
