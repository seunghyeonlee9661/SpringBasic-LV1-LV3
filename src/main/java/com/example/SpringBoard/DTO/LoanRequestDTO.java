package com.example.SpringBoard.DTO;

import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Member;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class LoanRequestDTO {
    private int id;
    private Book book;
    private Member member;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public LoanRequestDTO(Member member,Book book){
        this.book = book;
        this.member = member;
    }

}
