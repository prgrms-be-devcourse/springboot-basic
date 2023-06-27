package com.demo.voucher.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public enum VoucherType {

    FIXED_AMOUNT("1",
            "고정 할인 바우처",
            "할인 고정 금액은 1 이상의 정수여야 합니다.",
            (String inputAmount) -> Pattern.matches("^[1-9]\\d*$", inputAmount)),
    PERCENT_DISCOUNT("2",
            "비율 할인 바우처",
            "할인 비율은 1 이상 99 이하의 정수여야 합니다.",
            (String inputAmount) -> Pattern.matches("^(?:[1-9]|[1-9]\\d)$", inputAmount));


    private final String command;
    private final String voucherDescription;
    private final String amountDescription;
    private final Function<String, Boolean> amountValidation;


    VoucherType(String command, String description, String amountDescription, Function<String, Boolean> amountValidation) {
        this.command = command;
        this.voucherDescription = description;
        this.amountDescription = amountDescription;
        this.amountValidation = amountValidation;
    }

    public static Optional<VoucherType> getVoucherTypeByCommand(String inputVoucherType) {
        return Arrays.stream(values())
                .filter(v -> v.getCommand().equals(inputVoucherType))
                .findFirst();
    }


    public boolean validateAmount(String amount) {
        return amountValidation.apply(amount);
    }

    public String getAmountDescription() {
        return amountDescription;
    }

    private String getCommand() {
        return command;
    }

    public String getVoucherDescription() {
        return voucherDescription;
    }

    @Override
    public String toString() {
        return command + ": " + voucherDescription;
    }
}
