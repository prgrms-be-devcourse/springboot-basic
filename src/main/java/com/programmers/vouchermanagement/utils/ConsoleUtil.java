package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.message.ErrorMessage;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ConsoleUtil {
    private static final int NAME_MIN_LENGTH = 1;
    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUtil.class);

    public static void displayMessage(String message) {
        textIO.getTextTerminal().println(message);
    }

    public static UUID getUUID() {
        String uuidString = textIO.newStringInputReader().read("UUID: ");
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid UUID: {}", uuidString);
            throw new IllegalArgumentException(ErrorMessage.INVALID_UUID.getMessage());
        }
    }

    public static UUID getUUID(String message) {
        displayMessage(message);
        return getUUID();
    }

    public static String getName() {
        return textIO.newStringInputReader()
                .withMinLength(NAME_MIN_LENGTH)
                .read("Name: ");
    }

    public static String getName(String message) {
        displayMessage(message);
        return getName();
    }
}
