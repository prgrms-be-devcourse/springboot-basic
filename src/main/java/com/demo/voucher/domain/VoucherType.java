package com.demo.voucher.domain;

import com.demo.voucher.domain.dto.VoucherCommandDescriptionDto;
import com.demo.voucher.exception.AmountInputException;
import com.demo.voucher.exception.VoucherTypeInputException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
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

    public static VoucherType of(String inputVoucherType) throws IllegalArgumentException {
        if (VOUCHER_TYPE_MAP.containsKey(inputVoucherType)) {
            return VOUCHER_TYPE_MAP.get(inputVoucherType);
        }
        throw new VoucherTypeInputException();
    }

    public boolean isValidAmount(String amount) {
        if (Boolean.FALSE.equals(amountValidation.apply(amount))) {
            throw new AmountInputException();
        }
        return true;
    }

    public VoucherCommandDescriptionDto getVoucherCommandAndDescription() {
        return new VoucherCommandDescriptionDto(command, voucherDescription);
    }
}
