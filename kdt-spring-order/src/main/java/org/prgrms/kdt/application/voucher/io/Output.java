package org.prgrms.kdt.application.voucher.io;

public interface Output {
    void printProgramName(); // 프로그램명

    void printCommandList(); // 명령어 목록

    void printInputCommandError(); // 잘못된 명령어 입력

    void printExit(); // 프로그램 종료
}
