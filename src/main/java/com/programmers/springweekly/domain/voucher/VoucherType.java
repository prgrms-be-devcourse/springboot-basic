package com.programmers.springweekly.domain.voucher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum VoucherType {
    FIXED,
    PERCENT;

    public static VoucherType from(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Input : {}, 사용자가 입력한 바우처 타입이 존재하지 않아서 발생한 예외 ", type, e);
            throw new IllegalArgumentException("Input: " + type + ", 찾으시는 바우처 타입이 없습니다.");
        }
    }

}
