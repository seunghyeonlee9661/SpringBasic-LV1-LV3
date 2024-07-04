package com.example.SpringBoard.DTO;
import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Lecture;
import com.example.SpringBoard.entity.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureResponseDTO {
    private int lecture_id;
    private String title;
    private int price;
    private String introduction;
    private Teacher teacher;
    private LocalDateTime regist;

    public LectureResponseDTO(Lecture lecture){
        this.lecture_id = lecture.getLecture_id();
        this.title = lecture.getTitle();
        this.price = lecture.getPrice();
        this.introduction = lecture.getIntroduction();
        this.teacher = lecture.getTeacher();
        this.regist = lecture.getRegist();
    }
    public LectureResponseDTO(int lecture_id, String title, int price, String introduction, Teacher teacher, LocalDateTime regist) {
        this.lecture_id = lecture_id;
        this.title = title;
        this.price = price;
        this.introduction = introduction;
        this.teacher = teacher;
        this.regist = regist;
    }
}
