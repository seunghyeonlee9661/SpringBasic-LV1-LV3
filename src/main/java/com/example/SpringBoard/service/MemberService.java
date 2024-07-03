package com.example.SpringBoard.service;

import com.example.SpringBoard.DTO.BookRequestDTO;
import com.example.SpringBoard.DTO.BookResponseDTO;
import com.example.SpringBoard.DTO.MemberRequestDTO;
import com.example.SpringBoard.DTO.MemberResponseDTO;
import com.example.SpringBoard.entity.Book;
import com.example.SpringBoard.entity.Member;
import com.example.SpringBoard.repository.BookRepository;
import com.example.SpringBoard.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public MemberResponseDTO create(MemberRequestDTO memberRequestDTO){
        Member member = memberRepository.save(new Member(memberRequestDTO));
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO(member);
        return memberResponseDTO;
    }

    public Optional<Member> getMember(String id){
        return memberRepository.findById(Integer.valueOf(id));
    }

}
