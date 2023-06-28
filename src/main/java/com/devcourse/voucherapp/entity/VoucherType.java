package com.devcourse.voucherapp.entity;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.entity.voucher.FixDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.PercentDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.exception.VoucherInputException;
import java.util.Arrays;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum VoucherType {
    FIX(
            "1",
            "고정 할인",
            "\n고정 할인 금액을 입력하세요. (1이상의 자연수, 단위: 원)",
            FixDiscountVoucher::new
    ),
    PERCENT(
            "2",
            "비율 할인",
            "\n비율 할인 퍼센트를 입력하세요. (1이상 100이하의 자연수, 단위: %)",
            PercentDiscountVoucher::new
    );

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "입력하신 할인권 방식은 없는 방식입니다.";

    private final String number;
    private final String name;
    private final String message;
    private final Function<String, Voucher> voucherGenerator;

    VoucherType(String number, String name, String message, Function<String, Voucher> voucherGenerator) {
        this.number = number;
        this.name = name;
        this.message = message;
        this.voucherGenerator = voucherGenerator;
    }

    public static VoucherType getVoucherType(String voucherTypeNumber) {
        return Arrays.stream(VoucherType.values())
                .filter(type -> voucherTypeNumber.equals(type.getNumber()))
                .findFirst()
                .orElseThrow(() -> new VoucherInputException(NOT_EXIST_VOUCHER_TYPE_MESSAGE));
    }

    public Voucher makeVoucher(String discountAmount) {
        return voucherGenerator.apply(discountAmount);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", number, name);
    }
}
