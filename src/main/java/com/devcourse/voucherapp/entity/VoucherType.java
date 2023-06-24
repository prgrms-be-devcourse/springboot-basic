package com.devcourse.voucherapp.entity;

import com.devcourse.voucherapp.exception.VoucherInputException;
import java.util.Arrays;

public enum VoucherType {
    FIX("1", "고정 할인"),
    PERCENT("2", "비율 할인");

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "입력하신 할인권 방식은 없는 방식입니다.";

    private final String number;
    private final String name;

    VoucherType(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static VoucherType getVoucherType(String voucherTypeNumber) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> voucherTypeNumber.equals(type.getNumber()))
            .findFirst()
            .orElseThrow(() -> new VoucherInputException(NOT_EXIST_VOUCHER_TYPE_MESSAGE));
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }
}
