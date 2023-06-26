package com.programmers.springweekly.domain;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ProgramMenu {
    EXIT("exit"),
    CREATE("create"),
    LIST("list"),
    CUSTOMER_BLACKLIST("blacklist");

    private final String menu;
    private static final Map<String, ProgramMenu> MENU_MAP =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(ProgramMenu::getProgramMenu, Function.identity())));

    ProgramMenu(String menu) {
        this.menu = menu;
    }

    public static ProgramMenu findProgramMenu(String type){
        if(MENU_MAP.containsKey(type)){
            return MENU_MAP.get(type);
        }

        throw new IllegalArgumentException("The type you are looking for is not found.");
    }

    public String getProgramMenu(){
        return menu;
    }
}
