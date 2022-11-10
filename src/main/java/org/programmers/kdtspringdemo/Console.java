package org.programmers.kdtspringdemo;

import org.programmers.kdtspringdemo.io.Input;
import org.programmers.kdtspringdemo.io.Output;

import java.util.Optional;
import java.util.Scanner;

public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String selectOption(String description) {
        System.out.print(description);
        return scanner.nextLine();
    }

    @Override
    public String selectVoucher(String description) {
        System.out.print(description);
        return scanner.nextLine();
    }

    @Override
    public Optional<Long> getVoucherInfo(String description) {
        System.out.print(description);
        try {
            Long voucherInfo = Long.parseLong(scanner.nextLine());
            return Optional.of(voucherInfo);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public void printErrorMessage() {
        System.out.println("잘못된 입력값 입니다. 다시 입력하세요.");
    }
}
