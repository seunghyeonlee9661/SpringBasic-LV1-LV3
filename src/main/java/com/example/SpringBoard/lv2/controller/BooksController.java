package com.example.SpringBoard.lv2.controller;

import com.example.SpringBoard.lv2.dto.MemberRequestDTO;
import com.example.SpringBoard.lv2.dto.MemberResponseDTO;
import com.example.SpringBoard.lv2.dto.BookRequestDTO;
import com.example.SpringBoard.lv2.dto.BookResponseDTO;
import com.example.SpringBoard.lv2.dto.LoanRequestDTO;
import com.example.SpringBoard.lv2.dto.LoanResponseDTO;
import com.example.SpringBoard.lv2.entity.Book;
import com.example.SpringBoard.lv2.entity.Loan;
import com.example.SpringBoard.lv2.entity.Member;
import com.example.SpringBoard.lv2.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
이노베이션 캠프 LV-2 : 도서 관리 서버
- 도서 등록 기능
- 도서 조회 기능 + (도서 대출 가능 여부 확인 )
- 도서 회원 등록 기능
- 도서 대출, 반납 기능 + ( 패널티 기능 )
- 대출 내역 조회 + (대출 내역 조회 조건 )
 */

@RequiredArgsConstructor
@org.springframework.stereotype.Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;

    /* 도서 목록 페이지 */
    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        model.addAttribute("menu","books");
        /* 페이지네이션을 위해 도서 목록을 받아옵니다. */
        Page<Book> paging = this.booksService.getBooks(page);
        /* 가져온 각 페이지 요소에 대출 가능 여부 적용 */
        paging.forEach(book -> {
            boolean isLoanable = this.booksService.checkLoanable(book.getId());
            book.setLoanable(isLoanable);
        });
        model.addAttribute("paging", paging);
        return "books/books";
    }

    /* 도서 추가 페이지 */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("menu","books");
        return "books/add";
    }

    /* 도서 추가 */
    @ResponseBody
    @PostMapping("/add")
    public BookResponseDTO add(@RequestBody BookRequestDTO bookRequestDTO) {
        /* 도서 추가 후 결과 반환 */
        return booksService.create(bookRequestDTO);
    }

    /* 회원 등록 페이지 */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("menu","books");
        return "books/signup";
    }

    /* 회원 등록 */
    @ResponseBody
    @PostMapping("/signup")
    public MemberResponseDTO signup(@RequestBody MemberRequestDTO memberRequestDTO) {
        return booksService.create(memberRequestDTO);
    }

    /* 도서 상세보기 */
    @GetMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id) {
        /* 도서 데이터 검색 */
        Book book = this.booksService.getBook(id);
        /* 해당 도서가 대출 가능한지 확인 */
        book.setLoanable(this.booksService.checkLoanable(id));
        /* 파라미터 전달 */
        model.addAttribute("menu","books");
        model.addAttribute("book", book);
        return "books/detail";
    }

    /* 도서 대출 */
    @ResponseBody
    @PostMapping("/detail/{id}/loan")
    public Map<String, Object> loanBook(@PathVariable("id") Integer id,@RequestBody Map<String,String> param) {
        /* 결과 반환을 위한 Map 초기화 */
        Map<String, Object> resultMap = new HashMap<>();
        /* 전달받은 ID 입력값 */
        String member_id = (String)param.get("member");

        /* member값이 존재하는지 확인 */
        Member booksMember = booksService.getMember(member_id);
        if(booksMember == null){
            resultMap.put("result","사용자가 존재하지 않습니다");
        }else{
            if(booksService.checkLoanable(member_id)){
                resultMap.put("result","미납 도서가 있어 대출이 불가능합니다.");
            }else{
                /* 도서가 존재하는지 확인 */
                Book book = booksService.getBook(id);
                if(book == null){
                    resultMap.put("result", "도서가 존재하지 않습니다.");
                }else{
                    /* 현재 패널티 상태인지 확인 / 날씨 값이 있으면 패널티 상태 */
                    LocalDate date = booksService.checkPanerty(member_id);
                    if(date != null){
                        resultMap.put("result","페널티 기간입니다." + date);
                    }else{
                        /* 대출 내역을 생성 */
                        if(booksService.create(new LoanRequestDTO(booksMember, book))){
                            resultMap.put("result","success");
                        }else{
                            resultMap.put("result","도서 대출 실패!");
                        }
                    }
                }
            }
        }
        /* 결과 반환 */
        return resultMap;
    }

    /* 도서 반납 */
    @ResponseBody
    @PostMapping("/detail/{id}/return")
    public Map<String, Object> returnBook(@PathVariable("id") Integer id, @RequestBody Map<String,String> param) {
        /* 결과 반환을 위한 Map 초기화 */
        Map<String, Object> resultMap = new HashMap<>();
        /* 전달받은 ID 입력값 */
        String member_id = (String)param.get("member");

        /* member값이 존재하는지 확인 */
        Member booksMember = booksService.getMember(member_id);
        if(booksMember == null){
            resultMap.put("result","사용자가 존재하지 않습니다");
        }else {
            /* 도서가 존재하는지 확인 */
            Book book = booksService.getBook(id);
            if (book == null) {
                resultMap.put("result", "도서가 존재하지 않습니다.");
            } else {
                /* 대출 내역을 받아와서 해당 내역의 사용자와 요청자가 일치하는지 확인 */
                if(this.booksService.checkLoan(book.getId(),member_id)){
                    resultMap.put("result","대출자가 아닙니다.");
                }else{
                    /* 반납일을 저장합니다 */
                    if (booksService.setReturn(id)){
                        resultMap.put("result","success");
                    } else {
                        resultMap.put("result","도서 반납 실패");
                    }
                }
            }
        }
        return resultMap;
    }

    /* 회원 대출 내역 조회 */
    @ResponseBody
    @PostMapping("/loan")
    public List<LoanResponseDTO> search(Model model, @RequestBody Map<String,String> param) {
        /* 대출 조건을 참거짓으로 전달받습니다 */
        boolean sort = Boolean.parseBoolean(param.get("sort"));

        /* 도서 대출 목록을 가져옵니다 */
        List<Loan> booksLoans = this.booksService.getLoans((String)param.get("id"));
        if(sort){
            /* 대출 조건에 따라 Return Date가 null인 대출만 뽑습니다 */
            booksLoans = booksLoans.stream().filter(loan -> loan.getReturnDate() == null).toList();
        }
        return booksLoans.stream().map(LoanResponseDTO::new).collect(Collectors.toList());
    }
}

