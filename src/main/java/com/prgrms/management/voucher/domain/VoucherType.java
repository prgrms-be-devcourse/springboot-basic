package com.prgrms.management.voucher.domain;

import com.prgrms.management.command.Command;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum VoucherType {
    FIXED, PERCENT;

    public static VoucherType of(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(e -> e.name().equals(input.toUpperCase()))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException("잘못된 명령어입니다."));
    }
}
