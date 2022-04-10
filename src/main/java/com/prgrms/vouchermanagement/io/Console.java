package com.prgrms.vouchermanagement.io;

import com.prgrms.vouchermanagement.Member;
import com.prgrms.vouchermanagement.StringUtils;
import com.prgrms.vouchermanagement.voucher.Voucher;
import org.springframework.stereotype.Component;

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

    /**
     * FixedDiscount 인 경우 할인 금액, PercentDiscount 인 경우 할인율을 입력 받는다.
     */
    @Override
    public int inputDiscount() throws InputMismatchException {
        System.out.print("input discount: ");
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
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type blacklist to list all black lists");
    }

    @Override
    public void printVoucherList(List<Voucher> vouchers) {
        if (vouchers == null || vouchers.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=== Voucher List ===").append("\n");
        vouchers.forEach(v -> sb.append("- ").append(v).append("\n"));
        System.out.println(sb);
    }

    @Override
    public void printMessage(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println();
    }

    @Override
    public void printBlackList(List<Member> blackList) {
        if (blackList == null || blackList.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=== Black List ===").append("\n");
        blackList.forEach(member -> sb.append("- ").append(member.getName()).append("\n"));
        System.out.println(sb);
    }
}
