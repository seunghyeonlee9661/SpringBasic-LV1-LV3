package com.example.SpringBoard.exceptions;

/* 도서 회원 중복 검사 Exception */
public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException(String message) {
        super(message);
    }
}
