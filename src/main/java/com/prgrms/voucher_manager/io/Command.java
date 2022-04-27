package com.prgrms.voucher_manager.io;

import java.util.Arrays;

public enum Command {
    VOUCHER,
    CUSTOMER,
    CREATE,
    LIST,
    BLACKLIST,
    RETURN,
    INSERT,
    DELETE,
    EXIT;


    public static Command getCommand(String inputString){
        return Arrays.stream(Command.values())
                .filter(c -> c.name().equals(inputString.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("Wrong Input"));

    }
}
