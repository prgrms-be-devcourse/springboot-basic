package com.programmers.part1.error.member;

/**
 * black member가 없을 경우
 * */
public class BlackListEmptyException extends RuntimeException{
    public BlackListEmptyException(String message) {
        super(message);
    }
}
