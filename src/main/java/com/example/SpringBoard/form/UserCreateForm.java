package com.example.SpringBoard.form;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "ID Required")
    private String username;

    @NotEmpty(message = "Password Required")
    private String password;

    @NotEmpty(message = "Password Check Required")
    private String password_check;

    @NotEmpty(message = "Email Required.")
    @Email
    private String email;
}
