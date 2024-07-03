package com.example.SpringBoard.repository;

import com.example.SpringBoard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
