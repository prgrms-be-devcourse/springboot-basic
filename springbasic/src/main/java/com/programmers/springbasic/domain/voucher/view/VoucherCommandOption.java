package com.programmers.springbasic.domain.voucher.view;

import lombok.Getter;

@Getter
public enum VoucherCommandOption {
    EXIT("exit"), CREATE("create"), LIST("list"), READ("read"), UPDATE("update"), DELETE("delete"), SHOW("show");

    String command;

    VoucherCommandOption(String command) {
        this.command = command;
    }
}
