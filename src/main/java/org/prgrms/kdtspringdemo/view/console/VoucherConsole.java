package org.prgrms.kdtspringdemo.view.console;

import org.prgrms.kdtspringdemo.view.console.input.Input;
import org.prgrms.kdtspringdemo.view.console.output.Output;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherDto;

public class VoucherConsole {
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
    private static final long FIXED_MIN_AMOUNT = 0L;
    private static final long FIXED_MAX_AMOUNT = 100000L;
    private static final String IS_NUMERIC_EXCEPTION_MESSAGE = "바우처의 할인 정책은 숫자를 입력해주세요.\n";
    private static final String OUT_OF_AMOUNT_RANGE_MESSAGE = "범위를 초과 하였습니다.\n";
    private static final String PERCENT_AMOUNT_VOUCHER_MESSAGE = "퍼센트 할인 금액을 입력하세요.\n";
    private static final long PERCENT_MIN_AMOUNT = 0L;
    private static final long PERCENT_MAX_AMOUNT = 100L;
    private static final String SUCCESS_CREATED_FIXED_VOUCHER = """
            type : %s
            discount : %s 원
            """;
    private static final String SUCCESS_CREATED_PERCENT_VOUCHER = """
            type : %s
            discount : %s 퍼센트
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
        if (voucherType == VoucherType.FIXED) {
            output.write(FIXED_AMOUNT_VOUCHER_MESSAGE);
            String inputAmount = input.read();
            validateAmount(inputAmount, FIXED_MIN_AMOUNT, FIXED_MAX_AMOUNT);
            amount = Long.parseLong(inputAmount);
        }

        if (voucherType == VoucherType.PERCENT) {
            output.write(PERCENT_AMOUNT_VOUCHER_MESSAGE);
            String inputAmount = input.read();
            validateAmount(inputAmount, PERCENT_MIN_AMOUNT, PERCENT_MAX_AMOUNT);
            amount = Long.parseLong(inputAmount);
        }

        return amount;
    }

    private void validateAmount(String input, final long minAmount, final long maxAmount) {
        if (!isNumeric(input)) {
            return;
        }

        long parsedInput = Long.parseLong(input);
        if (checkAmountRange(parsedInput, minAmount, maxAmount)) {
            throw new IllegalArgumentException(OUT_OF_AMOUNT_RANGE_MESSAGE);
        }
    }

    private boolean isNumeric(String amount) {
        try {
            Long.parseLong(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(IS_NUMERIC_EXCEPTION_MESSAGE);
        }

        return true;
    }

    private boolean checkAmountRange(long input, final long minAmount, final long maxAmount) {
        return input <= minAmount && input > maxAmount;
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
