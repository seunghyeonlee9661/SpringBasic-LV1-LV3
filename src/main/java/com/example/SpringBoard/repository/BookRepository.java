package com.example.SpringBoard.repository;

import com.example.SpringBoard.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/*
이노베이션 캠프 LV-2 : 도서 Repository
 */

public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAllByOrderByRegistDateAsc(Pageable pageable);
}
