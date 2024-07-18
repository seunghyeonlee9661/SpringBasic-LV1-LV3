package com.example.SpringBoard.lv2.service;
import com.example.SpringBoard.lv2.dto.*;
import com.example.SpringBoard.lv2.entity.Book;
import com.example.SpringBoard.lv2.entity.Loan;
import com.example.SpringBoard.lv2.entity.Member;
import com.example.SpringBoard.lv2.repository.BookRepository;
import com.example.SpringBoard.lv2.repository.LoanRepository;
import com.example.SpringBoard.lv2.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

/* 이노베이션 캠프 LV-2 : 도서 Service */
@Service
@RequiredArgsConstructor
public class BooksService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    
    /*____________________________도서___________________________________*/

    /* 도서 추가 */
    @Transactional
    public ResponseEntity<Integer> createBook(BookRequestDTO bookRequestDTO){
        Book book = bookRepository.save(new Book(bookRequestDTO));
        return ResponseEntity.ok(book.getId());
    }

    /* 특정 도서 불러오기 */
    public ResponseEntity<BookResponseDTO> getBook(int id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("선택한 도서는 존재하지 않습니다."));
        return ResponseEntity.ok(new BookResponseDTO(book, isBookLoanable(book.getId())));
    }

    /* 페이지에 해당하는 도서 목록 불러오기 */
    public ResponseEntity<Page<BookResponseDTO>> getBookList(int page) {
        Page<Book> bookPage = bookRepository.findAllByOrderByRegistDateAsc(PageRequest.of(page, 10));
        return ResponseEntity.ok(bookPage.map(book -> new BookResponseDTO(book, isBookLoanable(book.getId()))));
    }

    /* 도서의 대출 가능 여부 확인 */
    private boolean isBookLoanable(int book_id) {
        return loanRepository.findTopByBookIdOrderByLoanDateDesc(book_id)
                .map(loan -> loan.getReturnDate() != null)
                .orElse(true);
    }

    /*____________________________회원___________________________________*/

    /* 회원 데이터 생성 */
    @Transactional
    public ResponseEntity<String> createMember(MemberRequestDTO memberRequestDTO){
        memberRepository.save(new Member(memberRequestDTO));
        return ResponseEntity.ok("회원 데이터 생성");
    }
    
    /*____________________________대출___________________________________*/

    /* 대출 기록 생성 : 대출 */
    @Transactional
    public ResponseEntity<String> createLoan(LoanRequestDTO loanRequestDTO){
        Book book = bookRepository.findById(loanRequestDTO.getBook_id()).orElseThrow(() -> new IllegalArgumentException("선택한 도서는 존재하지 않습니다."));
        Member member = memberRepository.findById(loanRequestDTO.getMember_id()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        // 사용자가 현재 다른 도서를 대출중
        Optional<Loan> loanOptional;
        loanOptional = loanRepository.findTopByMemberIdOrderByLoanDateDesc(member.getId());
        if(loanOptional.isPresent() && loanOptional.get().getReturnDate() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자가 현재 대출중인 도서가 있습니다.");
        }
        // 사용자가 패널티 상태
        loanOptional = loanRepository.findTopByMemberIdOrderByReturnDateDesc(member.getId());
        if(loanOptional.isPresent()){
            Loan loan = loanOptional.get();
            if(DAYS.between(loan.getLoanDate().toLocalDate(), loan.getReturnDate().toLocalDate()) >= 7 && DAYS.between(LocalDate.now(), loan.getReturnDate().toLocalDate()) <= 14){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자는 패널티 상태입니다. : " + loan.getReturnDate().toLocalDate().plusDays(14));
            }
        }
        // 도서가 현재 대출중
        if(!isBookLoanable(book.getId())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("도서는 현재 대출중입니다.");
        loanRepository.save(new Loan(book,member));
        return ResponseEntity.ok("대출 성공");
    }


    /* 대출 기록 수정 : 반납  */
    @Transactional
    public ResponseEntity<String> updateLoan(LoanRequestDTO loanRequestDTO){
        Loan loan = loanRepository.findTopByBookIdOrderByLoanDateDesc(loanRequestDTO.getBook_id()).orElseThrow(() -> new IllegalArgumentException("대출 정보를 확인할 수 없습니다."));
        if(!loan.getMember().getId().equals(loanRequestDTO.getMember_id())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자의 대출 도서가 아닙니다.");
        loan.updateReturnDate();
        return ResponseEntity.ok("반납되었습니다.");
    }


    /* 대출 기록 불러오기 */
    public ResponseEntity<List<LoanResponseDTO>> findLoanList(String member_id, boolean sort) {
        List<Loan> loans = member_id.isEmpty() ? loanRepository.findAllByOrderByLoanDateAsc() : loanRepository.findAllByMemberIdOrderByLoanDateAsc(member_id);
        Stream<Loan> loanStream = loans.stream();
        if (sort) loanStream = loanStream.filter(loan -> loan.getReturnDate() == null);
        return ResponseEntity.ok(loanStream.map(LoanResponseDTO::new).collect(Collectors.toList()));
    }
}
