package com.example.SpringBoard.lv3.dto;

import com.example.SpringBoard.lv3.entity.Lecture;
import com.example.SpringBoard.lv3.entity.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SimpleLectureDTO {
    private int id;
    private String title;
    private int price;
    private String introduction;
    private String category;
    private LocalDateTime regist;

    public SimpleLectureDTO(Lecture lecture){
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.price = lecture.getPrice();
        this.introduction = lecture.getIntroduction();
        this.category = lecture.getCategory();
        this.regist = lecture.getRegist();
    }
}
