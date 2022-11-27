package com.example.springbootbasic.util;

public enum CharacterUnit {
    SPACE(" "),
    ENTER(System.lineSeparator()),
    PERCENT("%"),
    EMPTY(""),
    STICK("-");

    private final String unit;

    CharacterUnit(String unit) {
        this.unit = unit;
    }

    public String unit() {
        return unit;
    }
}
