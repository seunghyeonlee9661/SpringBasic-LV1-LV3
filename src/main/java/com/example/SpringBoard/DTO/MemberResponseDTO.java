package com.example.SpringBoard.DTO;

import com.example.SpringBoard.entity.Member;

public class MemberResponseDTO {
    private String id;
    private String name;
    private String gender;
    private String phoneNumber;
    private String address;

    public MemberResponseDTO(String id, String name, String gender, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getGender();
        this.phoneNumber = member.getPhoneNumber();
        this.address = member.getAddress();
    }


}
