package org.prgrms.kdt.io;

import lombok.Getter;

@Getter
public enum Command {
    CREATE("create"),
    LIST("list"),
    EXIT("exit"),
    HELP("help"),
    BLACK("black"),
    FAIL("");

    private String command;

    Command(String command) {
        this.command = command;
    }

}
