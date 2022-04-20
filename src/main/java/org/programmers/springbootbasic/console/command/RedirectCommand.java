package org.programmers.springbootbasic.console.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedirectCommand implements Command {
    CREATE_AMOUNT("create-amount"),
    CREATE_COMPLETE("create-complete"),
    ERROR("error");

    private final String viewName;
}