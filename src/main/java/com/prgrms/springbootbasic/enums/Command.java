package com.prgrms.springbootbasic.enums;

public enum Command {
    EXIT,
    CREATE,
    LIST;

    public static Command of(String inputCommand) {
        try {
            return Command.valueOf(inputCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 명령어 입력입니다. 프로그램 종료(exit),바우처 생성(create), 바우처 목록 조회(list) 중 하나를 입력해주세요.");
        }
    }
}