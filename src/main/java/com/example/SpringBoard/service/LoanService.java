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
        if(id.equals("")){
            return loanRepository.findAllByOrderByLoanDateAsc();
        }
        return loanRepository.findByMemberIdOrderByLoanDateAsc(id);
    }

    /* 도서 아이디로 가장 최근 도서 대출 항목 */
    public boolean checkLoan(int book,String member) {
        Optional<Loan> optionalLoan = loanRepository.findTopByBookIdOrderByLoanDateDesc(book);
        return optionalLoan.isPresent() && !optionalLoan.get().getMember().getId().equals(member);
    }

    /* 도서 아이디로 대출 가능 여부 확인 -> 대출 내역에 반납 일자가 없는 경우 */
    public boolean checkLoanable(int id) {// DB 조회
        Optional<Loan> loanOptional = loanRepository.findTopByBookIdOrderByLoanDateDesc(id);
        return loanOptional.isEmpty() || loanOptional.get().getReturnDate() != null;
    }

    /* 사용자 아이디로 대출 가능 여부 확인 -> 대출 내역이 있는데 반납 내역이 없는 경우 => 미납 */
    public boolean checkLoanable(String id) {// DB 조회
        Optional<Loan> loanOptional = loanRepository.findTopByMemberIdOrderByLoanDateDesc(id);
        return loanOptional.isPresent() && loanOptional.get().getReturnDate() == null;
    }

    /* 대출 기록 생성 */
    public boolean create(LoanRequestDTO loanRequestDTO){
        Loan loan = loanRepository.save(new Loan(loanRequestDTO));
        return true;
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
        /* 해당 대출 내역 확인 */
        Optional<Loan> loanOptional = loanRepository.findTopByMemberIdOrderByReturnDateDesc(id);
        if(loanOptional.isPresent()){
            Loan loan = loanOptional.get();
            /* (최근 대출 내역 - 최근 반납 내역 >= 7) 이면서 (최근 대출 내역 - 오늘 <= 14(2주)) 일 경우 -> 패널티로 간주하고 대출 거절함 */
            if(DAYS.between(loan.getLoanDate().toLocalDate(), loan.getReturnDate().toLocalDate()) >= 7 && DAYS.between(LocalDate.now(),loan.getReturnDate().toLocalDate()) <= 14){
                return loan.getReturnDate().toLocalDate().plusDays(14);
            }
        }
        return null;
    }
}
