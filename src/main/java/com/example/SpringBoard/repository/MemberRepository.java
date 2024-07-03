package com.example.SpringBoard.repository;

import com.example.SpringBoard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/*
이노베이션 캠프 LV-2 : 도서 회원 Repository
 */

public interface MemberRepository extends JpaRepository<Member, Integer> {
    boolean existsById(String memberId);
    boolean existsByphoneNumber(String phone);
}
