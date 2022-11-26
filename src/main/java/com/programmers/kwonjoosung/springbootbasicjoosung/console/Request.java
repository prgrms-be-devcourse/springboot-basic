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

    public static Request of(String input,String Delimiter) {
        String[] split = input.split(Delimiter);
        Domain domain = Domain.of(split[0]);
        Command command = Command.of(split[1]);
        return new Request(domain, command);
    }

}
