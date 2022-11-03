package com.programmers.commandLine.global.io;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

@Component
public class Console {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void print(String message) {
        System.out.print(MessageFormat.format("{0}\n", message));
    }

    public String read() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void printMenu() {
        System.out.println("""
                입력 예시 : exit, create, list
                
                === 바우처 프로그램 ===
                프로그램을 종료하려면 exit를 입력하세요!
                create를 입력하여 새 바우처를 만듭니다.
                모든 바우처를 나열하려면 list를 입력하십시오.
                
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """);
    }

    public void printVoucher() {
        System.out.println("""
                1. FixedAmountVoucher Create
                2. PercentDiscountVoucher Create
                """);
    }
}
