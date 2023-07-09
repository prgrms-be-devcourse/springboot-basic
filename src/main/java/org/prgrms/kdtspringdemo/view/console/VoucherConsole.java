package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.voucher.constant.CommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class VoucherConsole {
    private static final Logger logger = LoggerFactory.getLogger(VoucherConsole.class);
    private static final String AMOUNT_VOUCHER_MESSAGE = "할인 금액을 입력하세요.\n";
    private static final String IS_NUMERIC_EXCEPTION_MESSAGE = "바우처의 할인 정책은 숫자를 입력해주세요.\n";
    private static final String SUCCESS_CREATED_VOUCHER = """
            type : %s
            discount amount : %s
            """;

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.print(message);
    }

    public CommandType inputCommand(String initMessage) {
        printMessage(initMessage);

        return CommandType.findCommandType(scanner.nextLine());
    }

    public VoucherType chooseVoucherType(String voucherChooseMessage) {
        printMessage(voucherChooseMessage);

        return VoucherType.findVoucherType(scanner.nextLine());
    }

    public Long inputAmountByVoucher() {
        printMessage(AMOUNT_VOUCHER_MESSAGE);
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

    public void printCreatedVoucher(VoucherType userVoucherType, long amount) {
        System.out.printf(SUCCESS_CREATED_VOUCHER, userVoucherType, amount);
    }
}
