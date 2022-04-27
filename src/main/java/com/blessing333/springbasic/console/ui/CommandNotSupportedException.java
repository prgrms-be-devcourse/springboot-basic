package com.blessing333.springbasic.console.ui;

public class CommandNotSupportedException extends RuntimeException{
    public CommandNotSupportedException() {
        super("지원하지 않는 명령어 입니다.");
    }
}
