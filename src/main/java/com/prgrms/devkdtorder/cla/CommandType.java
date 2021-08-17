package com.prgrms.devkdtorder.cla;


import java.util.Arrays;

public enum CommandType {
    CREATE,
    LIST,
    QUIT;


    public static boolean anyMatch(final String command){
        return Arrays.stream(CommandType.values())
                .anyMatch(e -> e.name().equals(command));
    }


}
