package com.example.SpringBoard.form;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostWriteForm {
    @Size(min = 1, max = 100)
    @NotEmpty(message = "Input Title Please")
    private String title;

    @NotEmpty(message = "Input Text Please")
    private String text;
}
