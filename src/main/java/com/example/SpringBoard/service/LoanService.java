package com.example.SpringBoard.service;

import com.example.SpringBoard.DTO.LoanRequestDTO;
import com.example.SpringBoard.DTO.LoanResponseDTO;
import com.example.SpringBoard.entity.Loan;
import com.example.SpringBoard.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getLoans(String id) {
        return loanRepository.findByMemberIdOrderByLoanDateDesc(id);
    }

    public boolean checkLoanable(int id) {// DB 조회
        Optional<Loan> loanOptional = loanRepository.findTopByBookIdOrderByLoanDateDesc(id);
        if (loanOptional.isPresent() && loanOptional.get().getReturnDate() == null ) return false;
        else return true;
    }

    public boolean create(LoanRequestDTO loanRequestDTO){
        Loan loan = loanRepository.save(new Loan(loanRequestDTO));
        return loan != null ;
    }

    public boolean setReturn(int id){
        Optional<Loan> loanOptional = loanRepository.findTopByBookIdOrderByLoanDateDesc(id);
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            loan.setReturnDate(LocalDateTime.now());
            loanRepository.save(loan);
            return true;
        }
        return  false;
    }
}
