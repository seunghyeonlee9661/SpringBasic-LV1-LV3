package com.example.SpringBoard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false, columnDefinition = "int")
    private Long id;

    @Column(unique = true,name="user_name", nullable = false, columnDefinition = "varchar(20)")
    private String username;

    @Column(name="user_password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(unique = true,name="user_email", nullable = false, columnDefinition = "varchar(255)")
    @Email
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> postList;

    public User() {
    }
}
