package com.example.SpringBoard.repository.books;

import com.example.SpringBoard.entity.books.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/*
이노베이션 캠프 LV-2 : 도서 회원 Repository
 */

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsById(String memberId);
    boolean existsByphoneNumber(String phone);
}
