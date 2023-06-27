package kr.co.programmers.school.voucher.domain.voucher.enums;

import kr.co.programmers.school.voucher.domain.voucher.exception.ApplicationException;
import kr.co.programmers.school.voucher.domain.voucher.exception.ErrorMessage;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1"),
    PERCENT_AMOUNT("2")
    ;

    private final String number;

    VoucherType(String number) {
        this.number = number;
    }

    private static final Map<String, VoucherType> VOUCHER_TYPE_MAP = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getNumber, Function.identity())));

    public static VoucherType from(String number) {
        return Optional.ofNullable(VOUCHER_TYPE_MAP.get(number))
                .orElseThrow(() -> new ApplicationException(ErrorMessage.NOT_FOUND_VOUCHER_TYPE));
    }

    public String getNumber() {
        return this.number;
    }

    public boolean isFixedAmount() {
        return FIXED_AMOUNT.equals(this);
    }

    public boolean isPercentAmount() {
        return PERCENT_AMOUNT.equals(this);
    }
}