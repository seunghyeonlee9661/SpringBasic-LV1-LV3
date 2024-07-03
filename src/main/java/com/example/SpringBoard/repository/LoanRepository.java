package com.example.SpringBoard.repository;

import com.example.SpringBoard.DTO.LoanResponseDTO;
import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByMemberIdOrderByLoanDateDesc(String memberId);
    Optional<Loan> findTopByBookIdOrderByLoanDateDesc(int bookid);

}
