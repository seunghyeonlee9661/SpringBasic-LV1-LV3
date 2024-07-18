package com.example.SpringBoard.lv2.entity;
import com.example.SpringBoard.lv2.dto.MemberRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
이노베이션 캠프 LV-2 : 도서 회원 Entity
 */

@Getter
@Entity
@NoArgsConstructor
public class Member {
    @Id
    @Column(name="id", nullable = false, columnDefinition = "varchar(50)")
    private String id;

    @Column(name="name", nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(name="gender", nullable = false, columnDefinition = "varchar(20)")
    private String gender;

    @Column(name="phoneNumber", nullable = false, columnDefinition = "varchar(20)", unique=true)
    private String phoneNumber;

    @Column(name="address", nullable = false, columnDefinition = "varchar(255)")
    private String address;

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();


    public Member(MemberRequestDTO requestDto) {
        this.id = requestDto.getId();
        this.name = requestDto.getName();
        this.gender = requestDto.getGender();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.address = requestDto.getAddress();
    }
}
