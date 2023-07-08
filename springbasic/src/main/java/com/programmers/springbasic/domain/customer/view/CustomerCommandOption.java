package com.programmers.springbasic.domain.customer.view;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CustomerCommandOption {
    EXIT("exit"), CREATE("create"), LIST("list"), READ("read"), UPDATE("update"), DELETE("delete"), SHOW("show");

    String command;

    CustomerCommandOption(String command) {
        this.command = command;
    }

    public static CustomerCommandOption of(String inputCommand) {
        return Arrays.stream(values())
                .filter(customerCommandOption -> customerCommandOption.name().equalsIgnoreCase(inputCommand))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong Command!"));
    }
}
