package com.example.SpringBoard.DTO.backoffice;
import com.example.SpringBoard.entity.backoffice.Lecture;
import com.example.SpringBoard.entity.backoffice.Teacher;
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

    public LectureResponseDTO(Lecture backofficeLecture){
        this.lecture_id = backofficeLecture.getLecture_id();
        this.title = backofficeLecture.getTitle();
        this.price = backofficeLecture.getPrice();
        this.introduction = backofficeLecture.getIntroduction();
        this.teacher = backofficeLecture.getTeacher();
        this.regist = backofficeLecture.getRegist();
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
