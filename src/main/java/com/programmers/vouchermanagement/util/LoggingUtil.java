package com.programmers.vouchermanagement.util;

public class LoggingUtil {
    public static String createRunLogMessage(String command) {
        return String.format("Run [%s] by %s on %s %s",
                command,
                System.getProperty("user.name"),
                System.getProperty("os.name"),
                System.getProperty("os.version"));
    }
}
