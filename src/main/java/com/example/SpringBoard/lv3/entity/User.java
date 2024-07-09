package com.example.SpringBoard.lv3.entity;

import com.example.SpringBoard.lv3.dto.SignupRequestDTO;
import jakarta.persistence.*;
import lombok.*;
/*
이노베이션 캠프 LV-3 : 관리자 Entity
 */

@Getter
@Entity
@NoArgsConstructor
@Table(name="backoffice_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", nullable = false, columnDefinition = "varchar(100)", unique = true)
    private String email;

    @Column(name="password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name="department", nullable = false, columnDefinition = "varchar(20)")
    private String department;

    @Column(name="role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    public User(SignupRequestDTO requestDto, String password) {
        this.email = requestDto.getEmail();
        this.password = password;
        this.department = requestDto.getDepartment();
        this.userRole = requestDto.getUserRole();
    }
}
