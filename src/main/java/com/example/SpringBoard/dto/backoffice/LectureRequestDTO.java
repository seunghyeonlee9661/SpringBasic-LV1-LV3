package com.example.SpringBoard.dto.backoffice;
import lombok.Getter;

@Getter
public class LectureRequestDTO {
    private String title;
    private int price;
    private String introduction;
    private String category;
    private int teacher_id;
}
