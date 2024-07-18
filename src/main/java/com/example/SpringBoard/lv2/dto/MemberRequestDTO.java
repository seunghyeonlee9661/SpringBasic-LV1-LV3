package com.example.SpringBoard.lv2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberRequestDTO {
    @NotBlank(message = "주민등록번호를 입력해주세요")
    private String id;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "성별을 선택해주세요")
    private String gender;
    @NotBlank(message = "전화번호를 입력해주세요")
    private String phoneNumber;
    @NotBlank(message = "주소를 입력해주세요")
    private String address;
}
