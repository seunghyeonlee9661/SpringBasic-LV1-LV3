package com.example.SpringBoard.DTO.backoffice;
import com.example.SpringBoard.entity.backoffice.Teacher;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureRequestDTO {
    private String title;
    private int price;
    private String introduction;
    private Teacher teacher;
    private LocalDateTime regist;
}
