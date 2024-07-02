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

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<Book> getBooks(int page) {// DB 조회
        Pageable pageable = PageRequest.of(page, 10,Sort.by("registDate").descending());
        return bookRepository.findAll(pageable);
    }

    public BookResponseDTO create(BookRequestDTO bookRequestDTO){
        Book book = bookRepository.save(new Book(bookRequestDTO));
        BookResponseDTO bookResponseDTO = new BookResponseDTO((book));
        return bookResponseDTO;
    }

    public Book getBook(int id){
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }

}
