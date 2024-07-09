package com.example.SpringBoard.entity.books;
import com.example.SpringBoard.dto.books.MemberRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/*
이노베이션 캠프 LV-2 : 도서 회원 Entity
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
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
    @Builder.Default
    private List<Loan> loans = new ArrayList<>();

    public Member() {
    }

    public Member(MemberRequestDTO requestDto) {
        this.id = requestDto.getId();
        this.name = requestDto.getName();
        this.gender = requestDto.getGender();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.address = requestDto.getAddress();

    }
}
