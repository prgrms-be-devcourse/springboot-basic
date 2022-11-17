package com.programmers.assignment.voucher.engine.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);


    @Override
    public String inputCommand(String commandMessage) {
        System.out.println(commandMessage);
        var inputCommand = scanner.nextLine();
        logger.info("Command input : " + inputCommand);
        return inputCommand.trim().toUpperCase();
    }

    @Override
    public String selectVoucher(String voucher) {
        System.out.println(voucher);
        var inputVoucher = scanner.nextLine();
        logger.info("Voucher select : " + inputVoucher);
        return inputVoucher.trim().toUpperCase();
    }

    @Override
    public String inputVoucherInfo(String voucherInfo) {
        System.out.println(voucherInfo);
        var inputVoucherInfo = scanner.nextLine();
        logger.info("Voucher info : " + inputVoucherInfo);
        return inputVoucherInfo;
    }
}
