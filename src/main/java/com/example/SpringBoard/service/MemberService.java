package com.example.SpringBoard.service;

import com.example.SpringBoard.DTO.MemberRequestDTO;
import com.example.SpringBoard.DTO.MemberResponseDTO;
import com.example.SpringBoard.entity.Member;
import com.example.SpringBoard.exceptions.DuplicateMemberException;
import com.example.SpringBoard.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
이노베이션 캠프 LV-2 : 회원 Service
 */
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 데이터 생성 */
    public MemberResponseDTO create(MemberRequestDTO memberRequestDTO){
        /* 회원 아이디 중복일 경우 */
        if(memberRepository.existsById(memberRequestDTO.getId())){
            throw new DuplicateMemberException("Member with this ID already exists");
        }
        /* 회원 전화번호 중복일 경으 */
        if(memberRepository.existsByphoneNumber(memberRequestDTO.getPhoneNumber())){
            throw new DuplicateMemberException("Member with this Phone Number already exists");
        }
        /* 데이터 입력 */
        Member member = memberRepository.save(new Member(memberRequestDTO));
        return new MemberResponseDTO(member);
    }

    /* 회원 주민등로번호로 회원 정보 호출 */
    public Optional<Member> getMember(String id){
        return memberRepository.findById(Integer.valueOf(id));
    }

}
