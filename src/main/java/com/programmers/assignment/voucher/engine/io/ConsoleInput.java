package com.programmers.assignment.voucher.engine.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public String inputCommand(String commandMessage) {
        System.out.println(commandMessage);
        var inputCommand = scanner.nextLine();
        return inputCommand.trim().toUpperCase();
    }

    public String selectVoucher(String voucher) {
        System.out.println(voucher);
        var inputVoucher = scanner.nextLine();
        return inputVoucher.trim().toUpperCase();
    }

    public String inputVoucherInfo(String voucherInfo) {
        System.out.println(voucherInfo);
        var inputVoucherInfo = scanner.nextLine();
        return inputVoucherInfo;
    }
}
