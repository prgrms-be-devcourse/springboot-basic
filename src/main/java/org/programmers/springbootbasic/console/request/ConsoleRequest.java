package org.programmers.springbootbasic.console.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.model.Model;

@Getter
@RequiredArgsConstructor
public class ConsoleRequest {
    private final Model model;
    private final Command command;
}
