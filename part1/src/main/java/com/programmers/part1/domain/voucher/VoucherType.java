package com.programmers.part1.domain.voucher;

import com.programmers.part1.exception.voucher.VoucherTypeMissingException;

import java.util.Arrays;

public enum VoucherType {

    FIXED("1","fix"),
    PERCENT("2","percent");

    private final String request;
    private final String typeString;

    VoucherType(String request,String typeString) {
        this.request = request;
        this.typeString = typeString;
    }

    public static VoucherType getVoucherTypeByRequest(String request) throws VoucherTypeMissingException{
        return Arrays.stream(values())
                .filter(type -> type.request.equals(request))
                .findAny()
                .orElseThrow(() -> new VoucherTypeMissingException("Voucher Type이 잘못 입력되었습니다."));
    }
}
