package org.programmers.springbootbasic.console.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RedirectCommand implements Command {
    CREATE_AMOUNT("create-amount"),
    CREATE_COMPLETE("create-complete"),
    ERROR("error"),
    WAIT_FOR_INPUT("wait-for-input");

    private final String viewName;
}
