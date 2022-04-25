package org.programmers.springbootbasic.console.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.model.ConsoleModel;

@Getter
@RequiredArgsConstructor
public class ConsoleRequest {
    private final ConsoleModel consoleModel;
    private final Command command;
}
