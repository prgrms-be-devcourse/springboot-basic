package com.prgrms.springbootbasic.console;

import org.springframework.stereotype.Component;

@Component
public class Printer {

    // String을 이어붙이는 것은 메모리 관리에 안좋다고는 하는데, IDE는 String 사용을 추천하네요...
    // Builder쓴 이유는 상수라 동기화 문제가 없지 않을까 생각돼서입니다.
    private static final String MENU = new StringBuilder().append("=== Voucher Program ===\n")
            .append("Type **exit** to exit the program.\n")
            .append("Type **create** to create a new voucher.\n")
            .append("Type **list** to list all vouchers.")
            .toString();
    private static final String EXIT = "Exit program. Bye.";

    public Printer() {
    }

    public void printMenu() {
        System.out.println(MENU);
    }

    public void printExitMessage() {
        System.out.println(EXIT);
    }

    public void printInvalidMessage(String message) {
        System.out.println(message);
    }
}
