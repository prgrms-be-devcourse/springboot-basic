package com.programmers.springbootbasic.io;

import java.util.Scanner;

public class ConsoleInput implements StandardInput {

    public static final String INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE = "잘못된 값입니다. 다시 입력하세요.";

    public final Scanner sc = new Scanner(System.in);

    @Override
    public String read() {
        return sc.next();
    }

    @Override
    public String inputCustomerId() {
        return promptInput("고객 아이디를 입력하세요.");
    }

    @Override
    public String inputCustomerName() {
        return promptInput("고객 이름을 입력하세요.");
    }

    @Override
    public String inputVoucherId() {
        return promptInput("할인권 아이디를 입력하세요.");
    }

    @Override
    public String promptInput(String promptMessage) {
        System.out.println(promptMessage);
        System.out.print("==> ");
        return sc.next();
    }

}
