package com.example.demo.util;

public enum CommandType {
    EXIT,
    CREATE,
    LIST;
    
    public static CommandType find(String input) {
        try {
            return valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("입력하신 " + input + "는 유효한 커멘드가 아닙니다.");
        }
    }
}
