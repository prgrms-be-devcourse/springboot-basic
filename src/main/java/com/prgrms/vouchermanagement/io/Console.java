package com.prgrms.vouchermanagement.io;

import com.prgrms.vouchermanagement.util.StringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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

    /**
     * @throws IllegalArgumentException : 입력받은 문자열이 UUID 형식이 아닌 경우 던져진다.
     */
    @Override
    public UUID inputUUID(String title) throws InputMismatchException {
        System.out.print(MessageFormat.format("input {0}: ", title));
        String input = sc.nextLine();

        if (!StringUtils.isUUID(input)) {
            throw new InputMismatchException("UUID 형식의 문자열이 아닙니다.");
        }

        return UUID.fromString(input);
    }

    @Override
    public String inputString(String title) {
        System.out.print(MessageFormat.format("input {0} : ", title));
        return sc.nextLine();
    }

    @Override
    public int inputNumber(String title) throws InputMismatchException {
        System.out.print(MessageFormat.format("input {0}: ", title));
        String input = sc.nextLine();

        // 숫자가 아닌 문자가 입력되면 예외를 던진다.
        if (!StringUtils.isNumber(input)) {
            throw new InputMismatchException();
        }

        return Integer.parseInt(input);
    }

    @Override
    public String inputCommand() {
        System.out.print("input: ");
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
}
