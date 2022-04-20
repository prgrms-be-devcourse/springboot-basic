package org.programmers.springbootbasic.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.console.command.Command;

@Getter
@RequiredArgsConstructor
public class ConsoleRequest {
    private final Model model;
    private final Command command;
}
