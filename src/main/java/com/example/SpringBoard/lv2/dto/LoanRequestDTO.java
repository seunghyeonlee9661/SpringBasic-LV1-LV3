package com.example.SpringBoard.lv2.dto;

import com.example.SpringBoard.lv2.entity.Book;
import com.example.SpringBoard.lv2.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoanRequestDTO {
    private int id;
    private Book book;
    private Member member;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public LoanRequestDTO(Member booksMember, Book book){
        this.book = book;
        this.member = booksMember;
    }

}
