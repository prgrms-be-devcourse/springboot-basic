package com.example.springbootbasic.util;

public enum CharacterUnit {
    SPACE(" "),
    ENTER("\n"),
    PERCENT("%"),
    EMPTY(""),
    STICK("-");

    private final String unit;

    CharacterUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
