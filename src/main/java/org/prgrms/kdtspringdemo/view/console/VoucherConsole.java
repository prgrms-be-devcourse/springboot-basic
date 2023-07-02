package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.view.console.input.Input;
import org.prgrms.kdtspringdemo.view.console.output.Output;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherConsole {
    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final String INIT_MESSAGE = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """;
    private static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.\n";
    private static final String INVALID_COMMAND_MESSAGE = "잘못된 명령입니다.\n";
    private static final String CHOICE_VOUCHER_TYPE_MESSAGE = "바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n";
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

    private final Input input;
    private final Output output;

    public VoucherConsole(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void printInitMessage() {
        output.write(INIT_MESSAGE);
    }

    public String inputCommand() {
        return input.read();
    }

    public void printSystemShutdown() {
        output.write(SYSTEM_SHUTDOWN_MESSAGE);
    }

    public String chooseVoucherType() {
        output.write(CHOICE_VOUCHER_TYPE_MESSAGE);

        return input.read();
    }

    public Long inputAmountByVoucher(VoucherType voucherType) {
        long amount = 0L;
        switch (voucherType) {
            case FIXED -> {
                amount = inputAmountEachVoucher(FIXED_AMOUNT_VOUCHER_MESSAGE);
            }
            case PERCENT -> {
                amount = inputAmountEachVoucher(PERCENT_AMOUNT_VOUCHER_MESSAGE);
            }
        }

        return amount;
    }

    private long inputAmountEachVoucher(String message) {
        output.write(message);
        String inputAmount = input.read();
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
        if (voucher.getVoucherType() == VoucherType.FIXED) {
            output.writeFormat(SUCCESS_CREATED_FIXED_VOUCHER, voucher.getVoucherType().name(), voucher.getAmount());
        }

        if (voucher.getVoucherType() == VoucherType.PERCENT) {
            output.writeFormat(SUCCESS_CREATED_PERCENT_VOUCHER, voucher.getVoucherType().name(), voucher.getAmount());
        }
    }

    public void printInvalidCommandSelected() {
        output.write(INVALID_COMMAND_MESSAGE);
    }
}
