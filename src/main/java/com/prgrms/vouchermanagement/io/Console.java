package com.prgrms.vouchermanagement.io;

import com.prgrms.vouchermanagement.util.StringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output{

    private final Scanner sc = new Scanner(System.in);

    /**
     * 추가할 voucher 의 번호를 입력 받는다.
     */
    @Override
    public int inputVoucherType() throws InputMismatchException{
        System.out.println();
        System.out.println("Select voucher type number to add");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("input type: ");
        String input = sc.nextLine();

        // 숫자가 아닌 문자가 입력되면 예외를 던진다.
        if (!StringUtils.isNumber(input)) {
            throw new InputMismatchException();
        }

        return Integer.parseInt(input);
    }

    @Override
    public String inputString(String title) {
        showTitle(title);
        return sc.nextLine();
    }

    @Override
    public int inputNumber(String title) throws InputMismatchException {
        showTitle(title);
        String input = sc.nextLine();

        // 숫자가 아닌 문자가 입력되면 예외를 던진다.
        if (!StringUtils.isNumber(input)) {
            throw new InputMismatchException();
        }

        return Integer.parseInt(input);
    }

    @Override
    public Long inputId(String title) throws InputMismatchException {
        showTitle(title);
        String input = sc.nextLine();

        // 숫자가 아닌 문자가 입력되면 예외를 던진다.
        if (!StringUtils.isNumber(input)) {
            throw new InputMismatchException();
        }

        return Long.parseLong(input);
    }

    @Override
    public String inputCommand() {
        showTitle("command");
        return sc.nextLine();
    }

    @Override
    public void printMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("input menu");
        System.out.println("- exit : exit the program.");
        System.out.println("- voucher : create a new voucher.");
        System.out.println("- customer : create a new customer.");
        System.out.println("- list : list all vouchers.");
        System.out.println("- blacklist : list all black lists");
        System.out.println("- wallet : go to wallet menu");
    }

    @Override
    public void printWalletMenu() {
        System.out.println("=== Wallet ===");
        System.out.println("input menu number");
        System.out.println("1. Add voucher to customer wallet");
        System.out.println("2. Find voucher in wallet");
        System.out.println("3. Remove voucher in wallet");
        System.out.println("4. Find customer who has specific voucher");
    }

    @Override
    public <T> void printList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        list.forEach(t -> sb.append("- ").append(t).append("\n"));
        System.out.println(sb);
    }

    @Override
    public void printMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    private void showTitle(String title) {
        System.out.print(MessageFormat.format("input {0}: ", title));
    }
}
