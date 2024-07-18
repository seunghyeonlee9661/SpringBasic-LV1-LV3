package com.example.SpringBoard.lv2.dto;

import com.example.SpringBoard.lv2.entity.Book;
import com.example.SpringBoard.lv2.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoanRequestDTO {
    private int book_id;
    private String member_id;
}
