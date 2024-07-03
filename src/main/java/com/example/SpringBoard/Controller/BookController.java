package com.example.SpringBoard.Controller;

import com.example.SpringBoard.DTO.*;
import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Loan;
import com.example.SpringBoard.entity.Member;
import com.example.SpringBoard.form.PostWriteForm;
import com.example.SpringBoard.service.BookService;
import com.example.SpringBoard.service.LoanService;
import com.example.SpringBoard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;
    private final LoanService loanService;

    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        model.addAttribute("menu","books");
        Page<Book> paging = this.bookService.getBooks(page);
        model.addAttribute("paging", paging);
        return "books/books";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("menu","books");
        return "books/add";
    }

    @ResponseBody
    @PostMapping("/add")
    public BookResponseDTO add(@RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.create(bookRequestDTO);
    }

    @GetMapping("/signup")
    public String signup(Model model, PostWriteForm postWriteForm) {
        model.addAttribute("menu","books");
        return "books/signup";
    }

    @ResponseBody
    @PostMapping("/signup")
    public MemberResponseDTO signup(@RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.create(memberRequestDTO);
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id) {
        Book book = this.bookService.getBook(id);
        book.setLoanable(this.loanService.checkLoanable(id));
        model.addAttribute("book", book);
        model.addAttribute("menu","books");
        return "books/detail";
    }

    @ResponseBody
    @PostMapping("/detail/{id}/loan")
    public Map<String, Object> loanBook(@PathVariable("id") Integer id,@RequestBody Map<String,String> param) {
        Map<String, Object> resultMap = new HashMap<>();
        Optional<Member> member = memberService.getMember((String)param.get("member"));
        if(member.isEmpty()){
            resultMap.put("result","Wrong Member ID");
        }else{
            Book book = bookService.getBook(id);
            if(book == null){
                resultMap.put("result","Book does not exist");
            }else{
                if(loanService.create(new LoanRequestDTO((Member) member.get(),book))){
                    resultMap.put("result","success");
                }else{
                    resultMap.put("result","Add Fail!");
                }
            }
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/detail/{id}/return")
    public Map<String, Object> returnBook(@PathVariable("id") Integer id, @RequestBody Map<String,String> param) {
        Map<String, Object> resultMap = new HashMap<>();
        if (loanService.setReturn(id)){
            resultMap.put("result","success");
        } else {
            resultMap.put("result","Return Fail!");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/loan")
    public List<LoanResponseDTO> search(Model model,@RequestBody Map<String,String> param) {
        List<Loan> loans = this.loanService.getLoans((String)param.get("id"));
//        for(Loan loan : loans){
//            System.out.println(loan.getId());
//            System.out.println(loan.getBook().getTitle());
//            System.out.println(loan.getMember().getName());
//            System.out.println(loan.getLoanDate());
//            System.out.println(loan.getReturnDate());
//            System.out.println("_____________________");
//        }
        return loans.stream().map(LoanResponseDTO::new).collect(Collectors.toList());
    }

//
//    @PostMapping("/write")
//    public String write(@Valid PostWriteForm postWriteForm, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "posts/write";
//        }
//        Post post = postService.create(postWriteForm.getTitle(),postWriteForm.getWriter(),postWriteForm.getPassword(),postWriteForm.getText());
//        return "redirect:/posts/detail/" + post.getId();
//    }
//
////    @GetMapping(value = "/detail/{id}")
////    public String detail(Model model, @PathVariable("id") Integer id) {
////        Post post = this.postService.getPost(id);
////        model.addAttribute("post", post);
////        model.addAttribute("menu","posts");
////        return "posts/detail";
////    }
//
//    @GetMapping(value = "/edit/{id}")
//    public String edit(Model model, @PathVariable("id") Integer id, PostWriteForm postWriteForm) {
//        Post post = this.postService.getPost(id);
//        postWriteForm.setText(post.getText());
//        postWriteForm.setTitle(post.getTitle());
//        postWriteForm.setWriter(post.getWriter());
//        postWriteForm.setPassword(post.getPassword());
//        model.addAttribute("menu","posts");
//        return "posts/write";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String edit(Model model,@Valid PostWriteForm postWriteForm, BindingResult bindingResult,@PathVariable("id") Integer id) {
//        if (bindingResult.hasErrors()) {
//            return "posts/write";
//        }
//        Post post = this.postService.getPost(id);
//        this.postService.edit(post, postWriteForm.getTitle(),postWriteForm.getText());
//        model.addAttribute("menu","posts");
//        return String.format("redirect:/posts/detail/%s",id);
//    }
//
//    @GetMapping("/delete/{id}")
//    public String questionDelete(@PathVariable("id") Integer id) {
//        Post post = this.postService.getPost(id);
//        this.postService.delete(post);
//        return "redirect:/posts";
//    }
}

