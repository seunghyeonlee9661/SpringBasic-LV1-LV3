package com.example.SpringBoard.DTO.books;

import com.example.SpringBoard.entity.books.Book;
import com.example.SpringBoard.entity.books.Loan;
import com.example.SpringBoard.entity.books.Member;
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

    public LoanResponseDTO(int id, Book book, String bookahthor, Member booksMember, String membername, String memberphone, LocalDateTime loanDate, LocalDateTime returnDate) {
        this.id = id;
        this.bookid = book.getId();
        this.bookTitle = book.getTitle();
        this.bookahthor = bookahthor;
        this.memberid = booksMember.getId();
        this.membername = membername;
        this.memberphone = memberphone;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public LoanResponseDTO(Loan booksLoan) {
        this.id = booksLoan.getId();
        this.bookid = booksLoan.getBook().getId();
        this.bookTitle = booksLoan.getBook().getTitle();
        this.memberid = booksLoan.getMember().getId();
        this.loanDate = booksLoan.getLoanDate();
        this.returnDate = booksLoan.getReturnDate();
        this.bookahthor = booksLoan.getBook().getAuthor();
        this.membername = booksLoan.getMember().getName();
        this.memberphone = booksLoan.getMember().getPhoneNumber();
    }



}
