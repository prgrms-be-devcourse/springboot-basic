package com.programmers.springbasic.domain.voucher.view;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VoucherCommandOption {
    EXIT("exit"), CREATE("create"), LIST("list"), READ("read"), UPDATE("update"), DELETE("delete"), SHOW("show");

    String command;

    VoucherCommandOption(String command) {
        this.command = command;
    }

    public static VoucherCommandOption of(String inputCommand) {
        return Arrays.stream(values())
                .filter(commandOption -> commandOption.name().equals(inputCommand.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong Command!"));
    }
}
