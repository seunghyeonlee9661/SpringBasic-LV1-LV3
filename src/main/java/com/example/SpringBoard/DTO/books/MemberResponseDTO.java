package com.example.SpringBoard.DTO.books;

import com.example.SpringBoard.entity.books.Member;
import lombok.Getter;

@Getter
public class MemberResponseDTO {
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final String address;

    public MemberResponseDTO(String name, String gender, String phoneNumber, String address) {
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public MemberResponseDTO(Member booksMember) {
        this.name = booksMember.getName();
        this.gender = booksMember.getGender();
        this.phoneNumber = booksMember.getPhoneNumber();
        this.address = booksMember.getAddress();
    }


}
