package org.prgrms.spring_week1.services.io;

import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Scanner;

@Service
public class ConsoleService implements Input, Output{

    private Scanner scanner = new Scanner(System.in);
    private Console console = System.console();

    @Override
    public String input() {
        return scanner.nextLine();

    }

    @Override
    public String inputInLine(){
        return console.readLine();
    }

    @Override
    public void output(String str) {
        System.out.println(str);
    }


    @Override
    public void mainMenu() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    @Override
    public void voucherCreate() {
        System.out.println("Type 1 for FixedAmountVoucher.\n" +
                    "Type 2 for PercentDiscountVoucher.");

    }

    @Override
    public void wrongInput(){
        System.out.println("입력이 잘못되었습니다. 다시 확인해주세요.");
    }

}
