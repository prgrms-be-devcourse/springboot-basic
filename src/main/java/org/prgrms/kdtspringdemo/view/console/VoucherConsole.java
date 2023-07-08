package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class VoucherConsole {
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final String FIXED_AMOUNT_VOUCHER_MESSAGE = "고정 할인 금액을 입력하세요.\n";
    private static final String IS_NUMERIC_EXCEPTION_MESSAGE = "바우처의 할인 정책은 숫자를 입력해주세요.\n";
    private static final String PERCENT_AMOUNT_VOUCHER_MESSAGE = "퍼센트 할인 금액을 입력하세요.\n";
    private static final String SUCCESS_CREATED_FIXED_VOUCHER = """
            type : %s
            discount amount : %s 원
            """;
    private static final String SUCCESS_CREATED_PERCENT_VOUCHER = """
            type : %s
            discount amount : %s 퍼센트
            """;

    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.print(message);
    }

    public String inputCommand(String initMessage) {
        printMessage(initMessage);

        return scanner.nextLine();
    }

    public String chooseVoucherType(String voucherChooseMessage) {
        printMessage(voucherChooseMessage);

        return scanner.nextLine();
    }

    public Long inputAmountByVoucher(VoucherType voucherType) {
        if (voucherType.isFixed()) {
            printMessage(FIXED_AMOUNT_VOUCHER_MESSAGE);
        } else if (voucherType.isPercent()) {
            printMessage(PERCENT_AMOUNT_VOUCHER_MESSAGE);
        }

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

    public void printCreatedVoucher(VoucherDto voucher) {
        if (voucher.getVoucherType().isFixed()) {
            System.out.printf(SUCCESS_CREATED_FIXED_VOUCHER, voucher.getVoucherType().name(), voucher.getAmount());
        } else if (voucher.getVoucherType().isPercent()) {
            System.out.printf(SUCCESS_CREATED_PERCENT_VOUCHER, voucher.getVoucherType().name(), voucher.getAmount());
        }
    }
}
