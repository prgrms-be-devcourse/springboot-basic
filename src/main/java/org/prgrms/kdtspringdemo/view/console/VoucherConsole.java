package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class VoucherConsole {
    private static final Logger logger = LoggerFactory.getLogger(VoucherConsole.class);
    private static final String IS_NUMERIC_EXCEPTION_MESSAGE = "바우처의 할인 정책은 숫자를 입력해주세요.\n";

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.print(message);
    }

    public MainCommandType inputMainCommand(String initMessage) {
        printMessage(initMessage);

        return MainCommandType.findCommandType(scanner.nextLine());
    }

    public VoucherCommandType inputVoucherCommand(String initMessage) {
        printMessage(initMessage);

        return VoucherCommandType.findCommandType(scanner.nextLine());
    }

    public VoucherType chooseVoucherType(String voucherChooseMessage) {
        printMessage(voucherChooseMessage);

        return VoucherType.findVoucherType(scanner.nextLine());
    }

    public Long inputAmountByVoucher(String mountVoucherMessage) {
        printMessage(mountVoucherMessage);
        String inputAmount = scanner.nextLine();
        validateAmountIsNumeric(inputAmount);

        return Long.parseLong(inputAmount);
    }

    private void validateAmountIsNumeric(String amount) {
        try {
            Long.parseLong(amount);
        } catch (NumberFormatException e) {
            logger.error("원인 : {} -> 에러 메시지 : {}", amount, IS_NUMERIC_EXCEPTION_MESSAGE);
            throw new IllegalArgumentException(IS_NUMERIC_EXCEPTION_MESSAGE);
        }
    }

    public void printCreatedVoucher(String successMessage, VoucherType userVoucherType, long amount) {
        System.out.printf(successMessage, userVoucherType, amount);
    }
}
