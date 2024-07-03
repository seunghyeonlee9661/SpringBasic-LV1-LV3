package com.example.SpringBoard.DTO;

import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Loan;
import com.example.SpringBoard.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoanResponseDTO {
    private int id;
    private int bookid;
    private String bookTitle;
    private String memberid;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public LoanResponseDTO(int id, Book book, Member member, LocalDateTime loanDate, LocalDateTime returnDate) {
        this.id = id;
        this.bookid = book.getId();
        this.bookTitle = book.getTitle();
        this.memberid = member.getId();
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public LoanResponseDTO(Loan loan) {
        this.id = loan.getId();
        this.bookid = loan.getBook().getId();
        this.bookTitle = loan.getBook().getTitle();
        this.memberid = loan.getMember().getId();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
    }



}
