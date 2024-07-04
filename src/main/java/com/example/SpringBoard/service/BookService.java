package com.example.SpringBoard.service;
import com.example.SpringBoard.DTO.BookRequestDTO;
import com.example.SpringBoard.DTO.BookResponseDTO;
import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Post;
import com.example.SpringBoard.exceptions.DataNotFoundException;
import com.example.SpringBoard.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
이노베이션 캠프 LV-2 : 도서 Service
 */

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

}
