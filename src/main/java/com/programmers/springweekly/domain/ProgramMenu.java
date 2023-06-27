package com.programmers.springweekly.domain;

public enum ProgramMenu {
    EXIT,
    CREATE,
    LIST,
    BLACKLIST;

    public static ProgramMenu findProgramMenu(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", The type you are looking for is not found.");
        }
    }
}
