package com.example.SpringBoard.Controller;

import com.example.SpringBoard.DTO.BookRequestDTO;
import com.example.SpringBoard.DTO.BookResponseDTO;
import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Post;
import com.example.SpringBoard.form.PostWriteForm;
import com.example.SpringBoard.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public String boards(Model model,@RequestParam(value="page", defaultValue="0") int page) {
        model.addAttribute("menu","books");
        Page<Book> paging = this.bookService.getBooks(page);
        model.addAttribute("paging", paging);

//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("books/books");
        return "books/books";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("menu","books");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("books/add");
        return "books/add";
    }

    @ResponseBody
    @PostMapping("/add")
    public BookResponseDTO add(@RequestBody BookRequestDTO bookRequestDTO) {
        return bookService.create(bookRequestDTO);
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model,@PathVariable("id") Integer id) {
        model.addAttribute("menu","books");
        return "books/add";
    }

    @ResponseBody
    @PostMapping("/detail/{id}/loan")
    public String loan(@PathVariable("id") Integer id) {
        return "books/add";
    }

    @ResponseBody
    @PostMapping("/detail/{id}/return")
    public String returnBook(@PathVariable("id") Integer id) {
        return "books/add";
    }

    @GetMapping("/signup")
    public String signup(Model model, PostWriteForm postWriteForm) {
        model.addAttribute("menu","books");
        return "posts/signup";
    }

    @ResponseBody
    @PostMapping("/signup")
    public String signup(PostWriteForm postWriteForm) {
        return "posts/signup";
    }

    @GetMapping("/loan")
    public String loan(Model model, PostWriteForm postWriteForm) {
        model.addAttribute("menu","books");
        return "posts/signup";
    }

    @ResponseBody
    @PostMapping("/loan")
    public String search(PostWriteForm postWriteForm) {
        return "posts/signup";
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

