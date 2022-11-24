package com.programmers.commandline.global.io;

public class ConsoleException extends RuntimeException {

    ConsoleException(String message, Exception e) {
        super(message, e);
    }
}
