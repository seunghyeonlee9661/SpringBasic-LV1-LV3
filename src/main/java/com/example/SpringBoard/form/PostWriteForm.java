package com.example.SpringBoard.form;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/*
이노베이션 캠프 LV-1 : 게시물 작성 폼
 */

@Getter
@Setter
public class PostWriteForm {
    @Size(min = 1, max = 100)
    @NotEmpty(message = "Input Title Please")
    private String title;

    @NotEmpty(message = "Input writer Please")
    private String writer;

    @NotEmpty(message = "Input Text Please")
    private String text;

    @NotEmpty(message = "Input password Please")
    private String password;
}
