package com.example.SpringBoard.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    private String id;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String image;

    public User() {
    }

    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

}
