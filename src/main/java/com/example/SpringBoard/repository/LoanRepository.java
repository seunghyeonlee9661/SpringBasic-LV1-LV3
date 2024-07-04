package com.example.SpringBoard.repository;

import com.example.SpringBoard.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/*
이노베이션 캠프 LV-2 : 대출 내역 Repository
 */

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByMemberIdOrderByLoanDateAsc(String memberId);
    List<Loan> findAllByOrderByLoanDateAsc();
    Optional<Loan> findTopByMemberIdOrderByReturnDateDesc(String memberId);
    Optional<Loan> findTopByMemberIdOrderByLoanDateDesc(String memberId);
    Optional<Loan> findTopByBookIdOrderByLoanDateDesc(int bookid);

}
