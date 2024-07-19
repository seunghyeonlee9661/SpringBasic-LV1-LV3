package com.example.SpringBoard.lv2.controller;

import com.example.SpringBoard.lv2.dto.*;
import com.example.SpringBoard.lv2.service.BooksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
이노베이션 캠프 LV-2 : 도서 관리 서버
- 도서 등록 기능
- 도서 조회 기능 + (도서 대출 가능 여부 확인 )
- 도서 회원 등록 기능
- 도서 대출, 반납 기능 + ( 패널티 기능 )
- 대출 내역 조회 + (대출 내역 조회 조건 )
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BooksRestController {
    private final BooksService booksService;
    
    /*____________________________도서___________________________________*/

    /* 도서 추가 */
    @ResponseBody
    @PostMapping("/book")
    public ResponseEntity<Integer> add(@RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return booksService.createBook(bookRequestDTO);
    }

    /* 도서 목록 */
    @GetMapping("/book")
    public  ResponseEntity<Page<BookResponseDTO>> findPosts(@RequestParam(value="page", defaultValue="0") int page) {
        return booksService.getBookList(page);
    }

    /* 도서 정보 */
    @GetMapping("/book/{id}")
    public ResponseEntity<BookResponseDTO> findPost(@PathVariable("id") int id) {
        return booksService.getBook(id);
    }

    /*____________________________회원___________________________________*/

    /* 회원 등록 */
    @ResponseBody
    @PostMapping("/member")
    public ResponseEntity<String> signup(@RequestBody @Valid MemberRequestDTO memberRequestDTO) {
        return booksService.createMember(memberRequestDTO);
    }

    /*____________________________대출___________________________________*/

    @ResponseBody
    @PostMapping("/loan")
    public ResponseEntity<String> createLoan(@RequestBody @Valid LoanRequestDTO loanRequestDTO) {
       return booksService.createLoan(loanRequestDTO);
    }


    /* 대출 목록 */
    @GetMapping("/loan")
    public  ResponseEntity<List<LoanResponseDTO>> findLoanList(@RequestParam(value="member_id", defaultValue="") String member_id,@RequestParam(value="sort", defaultValue="true") boolean sort) {
        return booksService.findLoanList(member_id,sort);
    }

    /* 대출 또는 반납 */
    @ResponseBody
    @PutMapping("/loan")
    public ResponseEntity<String> loanBook(@RequestBody @Valid LoanRequestDTO loanRequestDTO) {
        return booksService.updateLoan(loanRequestDTO);
    }
}

