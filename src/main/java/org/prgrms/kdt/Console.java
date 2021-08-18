package org.prgrms.kdt;

import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Console implements Input, Output {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    @Override
    public String input(String prompt) throws IOException {
        System.out.println(prompt);
        return br.readLine();
    }

    @Override
    public void inputError() {
        System.out.println("입력이 잘못되었습니다.");
    }

    @Override
    public void help() {
        System.out.println("\"=== Voucher Program ===\\n\" +\n" +
                "                \"Type exit to exit the program.\\n\" +\n" +
                "                \"Type create to create a new voucher.\\n\" +\n" +
                "                \"Type list to list all vouchers.\"");
    }
}
