package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.domain.Type;

public class CommandLineApplication implements Runnable{
    private final Console console;

    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run() {
        while (true) {
            Type type;
            try {
                type = console.getCondition();
            } catch (Exception e) {
                System.out.println("[Error Occurred] " + e.getMessage());
                continue;
            }
            showListOfVouchers(type);
            createVouchers(type);
            if (type == Type.EXIT) {
                System.out.println("프로그램이 종료됩니다.");
                break;
            }
        }
    }

    private static void showListOfVouchers(Type type) {
        if (type == Type.LIST) {
            System.out.println("LIST 로직 실행");
        }
    }

    private static void createVouchers(Type type) {
        if (type == Type.CREATE) {
            System.out.println("CREATE 실행");
        }
    }
}
