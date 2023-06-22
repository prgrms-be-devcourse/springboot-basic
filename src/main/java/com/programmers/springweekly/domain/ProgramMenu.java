package com.programmers.springweekly.domain;

import java.util.Arrays;

public enum ProgramMenu {
    EXIT("exit"), CREATE("create"), LIST("list");

    private String menu;

    ProgramMenu(String menu) {
        this.menu = menu;
    }

    public ProgramMenu findProgramMenu(ProgramMenu programMenu){
        return Arrays.stream(ProgramMenu.values())
                .filter(program -> program.menu.equals(programMenu))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("찾으시는 메뉴가 없습니다"));
    }
}
