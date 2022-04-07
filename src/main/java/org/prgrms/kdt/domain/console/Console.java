package org.prgrms.kdt.domain.console;

import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output{
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public String inputCommand() {
        return scanner.next();
    }

    @Override
    public void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void printAllVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void printWrongCommandError() {
        System.out.println("유효하지 않은 명령어 입니다");
    }
}
