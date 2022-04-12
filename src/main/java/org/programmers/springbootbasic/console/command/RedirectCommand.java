package org.programmers.springbootbasic.console.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RedirectCommand implements Command {
    WAIT_FOR_INPUT("wait-for-input"),
    CREATE_AMOUNT("create-amount"),
    CREATE_COMPLETE("create-complete");

    private final String name;

    @Override
    public String getCommandInformation() {
        return this.name;
    }
}
