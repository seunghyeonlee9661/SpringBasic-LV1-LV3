package com.example.SpringBoard.entity;

import com.example.SpringBoard.DTO.BookRequestDTO;
import com.example.SpringBoard.DTO.MemberRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Loan> loan = new ArrayList<>();

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
