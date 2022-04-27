package com.prgrms.management.command.domain;

import com.prgrms.management.config.ErrorMessageType;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Command {
    CREATEVOUCHER,
    LISTVOUCHER,
    BLACKLIST,
    CREATECUSTOMER,
    UPDATECUSTOMER,
    DELETECUSTOMER,
    DELETEALLCUSTOMER,
    FINDCUSTOMERBYID,
    FINDCUSTOMERBYEMAIL,
    LISTCUSTOMER,
    ASSIGNVOUCHER,
    DELETEVOUCHER,
    LISTVOUCHERWITHTYPE,
    EXIT;

    public static Command of(String input) {
        return Arrays.stream(Command.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException(Command.class+ ErrorMessageType.NOT_COMMAND_TYPE.getMessage()));
    }
}
