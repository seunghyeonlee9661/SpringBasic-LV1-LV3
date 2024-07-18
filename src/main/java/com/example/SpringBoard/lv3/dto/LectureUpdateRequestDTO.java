package com.example.SpringBoard.lv3.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LectureUpdateRequestDTO {
    @NotNull
    private int id;
    @NotBlank(message = "제목을 입력하세요")
    private String title;
    @NotNull(message = "가격을 입력하세요")
    private int price;
    @NotBlank(message = "소개글을 입력하세요")
    private String introduction;
    @NotBlank(message = "카테고리를 선택하세요")
    private String category;
    @NotNull(message = "강사를 선택하세요")
    private int teacher_id;
}
