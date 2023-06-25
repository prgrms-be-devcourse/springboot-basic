package com.programmers.springweekly.domain;

import java.util.Arrays;

public enum ProgramMenu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    CUSTOMER_BLACKLIST("blacklist");

    private String menu;

    ProgramMenu(String menu) {
        this.menu = menu;
    }

    public static ProgramMenu findProgramMenu(String programMenu){
        return Arrays.stream(ProgramMenu.values())
                .filter(program -> program.menu.equals(programMenu))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("There is no program menu."));
    }
}
