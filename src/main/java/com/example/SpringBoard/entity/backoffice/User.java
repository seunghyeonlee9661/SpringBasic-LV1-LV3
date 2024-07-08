package com.example.SpringBoard.entity.backoffice;

import com.example.SpringBoard.DTO.backoffice.SignupRequestDTO;
import com.example.SpringBoard.Enum.backoffice.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/*
이노베이션 캠프 LV-3 : 관리자 Entity
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
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
    private Role role;

    public User() {
    }

    public User(SignupRequestDTO requestDto) {
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.department = requestDto.getDepartment();
        this.role = requestDto.getRole();
    }
}
