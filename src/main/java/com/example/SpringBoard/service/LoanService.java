package com.example.SpringBoard.service;

import com.example.SpringBoard.DTO.LoanRequestDTO;
import com.example.SpringBoard.entity.Loan;
import com.example.SpringBoard.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

/*
이노베이션 캠프 LV-2 : 대출 내역 Service
 */

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /* 사용자 주민번호로 대출 목록 */
    public List<Loan> getLoans(String id) {
        return loanRepository.findByMemberIdOrderByLoanDateDesc(id);
    }

    /* 도서 아이디로 가장 최근 도서 대출 항목 */
    public boolean checkLoan(int book,String member) {
        Optional<Loan> optionalLoan = loanRepository.findTopByBookIdOrderByLoanDateDesc(book);
        return optionalLoan.isPresent() && !optionalLoan.get().getMember().getId().equals(member);
    }


    /* 도서 아이디로 대출 가능 여부 확인 */
    public boolean checkLoanable(int id) {// DB 조회
        Optional<Loan> loanOptional = loanRepository.findTopByBookIdOrderByLoanDateDesc(id);
        if (loanOptional.isPresent() && loanOptional.get().getReturnDate() == null ) return false;
        else return true;
    }

    /* 대출 기록 생성 */
    public boolean create(LoanRequestDTO loanRequestDTO){
        Loan loan = loanRepository.save(new Loan(loanRequestDTO));
        return loan != null ;
    }

    /* 기록의 반납일자를 추가 -> 반납 기능 */
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

    /* 페널티 상태를 확인하는 기능 */
    public LocalDate checkPanerty(String id){
        Optional<Loan> loanOptional = loanRepository.findTopByMemberIdOrderByReturnDateDesc(id);
        if(loanOptional.isPresent()){
            Loan loan = loanOptional.get();
            int between = loan.getLoanDate().compareTo(loan.getReturnDate());
            System.out.println("Final Return Period :" + DAYS.between(loan.getLoanDate().toLocalDate(), loan.getReturnDate().toLocalDate()));
            System.out.println("Panerty Period :" +  DAYS.between(LocalDate.now(),loan.getReturnDate().toLocalDate()));
            if(DAYS.between(loan.getLoanDate().toLocalDate(), loan.getReturnDate().toLocalDate()) >= 7 && DAYS.between(LocalDate.now(),loan.getReturnDate().toLocalDate()) <= 14){
                return loan.getReturnDate().toLocalDate().plusDays(14);
            }
        }
        return null;
    }
}
