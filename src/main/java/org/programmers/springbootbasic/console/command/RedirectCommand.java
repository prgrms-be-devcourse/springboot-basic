package org.programmers.springbootbasic.console.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedirectCommand implements Command {
    CREATE_VOUCHER_AMOUNT("create-amount"),
    CREATE_VOUCHER_COMPLETE("create-complete"),
    CREATE_MEMBER_EMAIL("create-email"),
    CREATE_MEMBER_COMPLETE("create-complete"),
    ERROR("error");

    private final String viewName;
}