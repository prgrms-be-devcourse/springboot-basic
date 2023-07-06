package com.devcourse.voucherapp.entity;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.voucher.FixDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.PercentDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.exception.VoucherTypeInputException;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum VoucherType {
    FIX(
            "1",
            "고정 할인",
            "\n고정 할인 금액을 입력하세요. (1이상의 자연수, 단위: 원)",
            FixDiscountVoucher::new,
            FixDiscountVoucher::new
    ),
    PERCENT(
            "2",
            "비율 할인",
            "\n비율 할인 퍼센트를 입력하세요. (1이상 100이하의 자연수, 단위: %)",
            PercentDiscountVoucher::new,
            PercentDiscountVoucher::new
    );

    private static final Map<String, VoucherType> VOUCHER_TYPES = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(VoucherType::getNumber, Function.identity())));

    private final String number;
    private final String name;
    private final String message;
    private final BiFunction<String, String, Voucher> voucherGenerator;
    private final TriFunction<String, String, Integer, Voucher> voucherDuplicator;

    VoucherType(String number, String name, String message, BiFunction<String, String, Voucher> voucherGenerator,
            TriFunction<String, String, Integer, Voucher> voucherDuplicator) {
        this.number = number;
        this.name = name;
        this.message = message;
        this.voucherGenerator = voucherGenerator;
        this.voucherDuplicator = voucherDuplicator;
    }

    public static VoucherType of(String voucherTypeNumber) {
        if (VOUCHER_TYPES.containsKey(voucherTypeNumber)) {
            return VOUCHER_TYPES.get(voucherTypeNumber);
        }

        throw new VoucherTypeInputException(voucherTypeNumber);
    }

    public Voucher makeVoucher(String discountAmount) {
        return voucherGenerator.apply(number, discountAmount);
    }

    public Voucher duplicateVoucher(String voucherId, String typeNumber, int discountAmount) {
        return voucherDuplicator.apply(voucherId, typeNumber, discountAmount);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", number, name);
    }
}
