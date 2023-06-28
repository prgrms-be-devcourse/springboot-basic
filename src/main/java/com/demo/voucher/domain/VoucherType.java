package com.demo.voucher.domain;

import com.demo.voucher.domain.dto.VoucherCommandDescriptionDto;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum VoucherType {

    FIXED_AMOUNT("1",
            "고정 할인 바우처",
            "할인 고정 금액은 1 이상의 정수여야 합니다. 할인 금액을 입력해주세요 : ",
            (String inputAmount) -> Pattern.matches("^[1-9]\\d*$", inputAmount)),
    PERCENT_DISCOUNT("2",
            "비율 할인 바우처",
            "할인 비율은 1 이상 99 이하의 정수여야 합니다. 할인 비율을 입력해주세요 : ",
            (String inputAmount) -> Pattern.matches("^(?:[1-9]|[1-9]\\d)$", inputAmount));


    private static final Map<String, VoucherType> VOUCHER_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getCommand, Function.identity())));

    private final String command;
    private final String voucherDescription;
    private final String requestAmountDescription;
    private final Function<String, Boolean> amountValidation;


    VoucherType(String command, String description, String requestAmountDescription, Function<String, Boolean> amountValidation) {
        this.command = command;
        this.voucherDescription = description;
        this.requestAmountDescription = requestAmountDescription;
        this.amountValidation = amountValidation;
    }

    public static Optional<VoucherType> getVoucherTypeByCommand(String inputVoucherType) {
        return Optional.ofNullable(VOUCHER_TYPE_MAP.get(inputVoucherType));
    }

    private String getCommand() {
        return command;
    }

    public boolean validateAmount(String amount) {
        return amountValidation.apply(amount);
    }

    public VoucherCommandDescriptionDto getVoucherCommandAndDescription() {
        return new VoucherCommandDescriptionDto(command, voucherDescription);
    }
}
