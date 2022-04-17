package com.programmers.part1.domain;

import com.programmers.part1.exception.voucher.VoucherTypeMissingException;

import java.util.Arrays;

public enum VoucherType {

    FIXED("1"),
    PERCENT("2");

    private final String request;

    VoucherType(String request) {
        this.request = request;
    }

    public static VoucherType getVoucherType(String request) throws VoucherTypeMissingException{
        return Arrays.stream(values())
                .filter(type -> type.request.equals(request))
                .findAny()
                .orElseThrow(() -> new VoucherTypeMissingException("Voucher Type이 잘못 입력되었습니다."));
    }

}
