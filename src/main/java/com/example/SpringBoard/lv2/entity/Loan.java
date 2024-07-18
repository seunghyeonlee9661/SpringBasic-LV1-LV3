package com.example.SpringBoard.lv2.entity;

import com.example.SpringBoard.lv2.dto.LoanRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
이노베이션 캠프 LV-2 : 대출 Entity
 */

@Getter
@Entity
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, columnDefinition = "int")
    private int id;

    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @CreationTimestamp
    @Column(name="loanDate")
    private LocalDateTime loanDate;

    @Column(name="returnDate")
    private LocalDateTime returnDate;

    public Loan(Book book, Member member) {
        this.book = book;
        this.member = member;
    }

    public void updateReturnDate(){
        returnDate = LocalDateTime.now();
    }
}
