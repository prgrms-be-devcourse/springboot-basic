package com.programmers.springbasic.domain.customer.view;

import lombok.Getter;

@Getter
public enum CustomerCommandOption {
    EXIT("exit"), CREATE("create"), LIST("list"), READ("read"), UPDATE("update"), DELETE("delete"), SHOW("show");

    String command;

    CustomerCommandOption(String command) {
        this.command = command;
    }
}
