package com.example.SpringBoard.DTO.books;

import com.example.SpringBoard.entity.books.Book;
import com.example.SpringBoard.entity.books.Member;
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
