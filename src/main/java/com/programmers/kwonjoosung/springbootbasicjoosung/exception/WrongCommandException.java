package com.programmers.kwonjoosung.springbootbasicjoosung.exception;

import java.text.MessageFormat;

public class WrongCommandException extends RuntimeException {
    public WrongCommandException(String command) {
        super(MessageFormat.format(" \"{0}\" 명령어는 올바르지 못한 명령어 입니다.", command));
    }
}
