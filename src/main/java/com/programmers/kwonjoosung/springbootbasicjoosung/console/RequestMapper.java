package com.programmers.kwonjoosung.springbootbasicjoosung.console;

import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Command;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Domain;

public class RequestMapper {
    public static Request map(String input,String Delimiter) {
        String[] split = input.split(Delimiter);
        Domain domain = Domain.of(split[0]);
        Command command = Command.of(split[1]);
        return new Request(domain, command);
    }
}
