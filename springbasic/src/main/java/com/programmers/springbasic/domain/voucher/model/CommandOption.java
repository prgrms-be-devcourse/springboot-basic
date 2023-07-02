package com.programmers.springbasic.domain.voucher.model;

import lombok.Getter;

@Getter
public enum CommandOption {
    EXIT("exit"), CREATE("create"), LIST("list");

    String command;

    CommandOption(String command) {
        this.command = command;
    }
}
