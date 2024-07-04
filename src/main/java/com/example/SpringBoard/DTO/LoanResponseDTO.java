package com.example.SpringBoard.DTO;

import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Loan;
import com.example.SpringBoard.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoanResponseDTO {
    private final int id;
    private final int bookid;
    private final String bookTitle;
    private final String bookahthor;
    private final String memberid;
    private final String membername;
    private final String memberphone;
    private final LocalDateTime loanDate;
    private final LocalDateTime returnDate;

    public LoanResponseDTO(int id, Book book, String bookahthor, Member member, String membername, String memberphone, LocalDateTime loanDate, LocalDateTime returnDate) {
        this.id = id;
        this.bookid = book.getId();
        this.bookTitle = book.getTitle();
        this.bookahthor = bookahthor;
        this.memberid = member.getId();
        this.membername = membername;
        this.memberphone = memberphone;
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
        this.bookahthor = loan.getBook().getAuthor();
        this.membername = loan.getMember().getName();
        this.memberphone = loan.getMember().getPhoneNumber();
    }



}
