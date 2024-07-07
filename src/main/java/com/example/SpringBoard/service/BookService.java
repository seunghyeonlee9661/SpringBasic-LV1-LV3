package com.example.SpringBoard.service;
import com.example.SpringBoard.DTO.books.MemberRequestDTO;
import com.example.SpringBoard.DTO.books.MemberResponseDTO;
import com.example.SpringBoard.DTO.books.BookRequestDTO;
import com.example.SpringBoard.DTO.books.BookResponseDTO;
import com.example.SpringBoard.DTO.books.LoanRequestDTO;
import com.example.SpringBoard.entity.books.Book;
import com.example.SpringBoard.entity.books.Loan;
import com.example.SpringBoard.entity.books.Member;
import com.example.SpringBoard.exceptions.DuplicateMemberException;
import com.example.SpringBoard.repository.books.BookRepository;
import com.example.SpringBoard.repository.books.LoanRepository;
import com.example.SpringBoard.repository.books.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

/*
이노베이션 캠프 LV-2 : 도서 Service
 */

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;

    public BookService(BookRepository bookRepository, LoanRepository loanRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
    }
    /* 페이지에 해당하는 도서 목록 불러오기 */
    public Page<Book> getBooks(int page) {// DB 조회
        Pageable pageable = PageRequest.of(page, 10,Sort.by("id").descending());
        return bookRepository.findAllByOrderByRegistDateAsc(pageable);
    }

    /* 도서 추가 */
    public BookResponseDTO create(BookRequestDTO bookRequestDTO){
        Book book = bookRepository.save(new Book(bookRequestDTO));
        return new BookResponseDTO(book);
    }

    /* 특정 도서 불러오기 */
    public Book getBook(int id){
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 도서는 존재하지 않습니다."));
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
        Loan booksLoan = loanRepository.save(new Loan(loanRequestDTO));
        return true;
    }

    /* 기록의 반납일자를 추가 -> 반납 기능 */
    public boolean setReturn(int id){
        Optional<Loan> loanOptional = loanRepository.findTopByBookIdOrderByLoanDateDesc(id);
        if (loanOptional.isPresent()) {
            Loan booksLoan = loanOptional.get();
            booksLoan.setReturnDate(LocalDateTime.now());
            loanRepository.save(booksLoan);
            return true;
        }
        return  false;
    }

    /* 페널티 상태를 확인하는 기능 */
    public LocalDate checkPanerty(String id){
        /* 해당 대출 내역 확인 */
        Optional<Loan> loanOptional = loanRepository.findTopByMemberIdOrderByReturnDateDesc(id);
        if(loanOptional.isPresent()){
            Loan booksLoan = loanOptional.get();
            /* (최근 대출 내역 - 최근 반납 내역 >= 7) 이면서 (최근 대출 내역 - 오늘 <= 14(2주)) 일 경우 -> 패널티로 간주하고 대출 거절함 */
            if(DAYS.between(booksLoan.getLoanDate().toLocalDate(), booksLoan.getReturnDate().toLocalDate()) >= 7 && DAYS.between(LocalDate.now(), booksLoan.getReturnDate().toLocalDate()) <= 14){
                return booksLoan.getReturnDate().toLocalDate().plusDays(14);
            }
        }
        return null;
    }

    /* 회원 데이터 생성 */
    public MemberResponseDTO create(MemberRequestDTO memberRequestDTO){
        /* 회원 아이디 중복일 경우 */
        if(memberRepository.existsById(memberRequestDTO.getId())){
            throw new DuplicateMemberException("Member with this ID already exists");
        }
        /* 회원 전화번호 중복일 경으 */
        if(memberRepository.existsByphoneNumber(memberRequestDTO.getPhoneNumber())){
            throw new DuplicateMemberException("Member with this Phone Number already exists");
        }
        /* 데이터 입력 */
        Member booksMember = memberRepository.save(new Member(memberRequestDTO));
        return new MemberResponseDTO(booksMember);
    }

    /* 회원 주민등로번호로 회원 정보 호출 */
    public Member getMember(String id) {
        return memberRepository.findById(id).orElse(null);
    }

}
