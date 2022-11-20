package com.programmers.kwonjoosung.springbootbasicjoosung.console;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Command;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Domain;

public class Request {
    private final Domain domain;
    private final Command command;

    public Request(Domain domain, Command command) {
        this.domain = domain;
        this.command = command;
    }

    public Domain getDomain() {
        return domain;
    }

    public Command getCommand() {
        return command;
    }
}
