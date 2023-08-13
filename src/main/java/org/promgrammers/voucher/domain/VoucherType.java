package org.promgrammers.voucher.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public enum VoucherType {
    FixedAmount("fixed"),
    PercentDiscount("percent");

    private final String value;



    public static VoucherType fromType(String value) {
        for (VoucherType type : VoucherType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                log.info("VoucherType.fromType - 값 변환: {} -> 타입: {}", value, type);
                return type;
            }
        }
        // 유효하지 않은 값에 대한 로그 처리
        log.warn("VoucherType.fromType - 유효하지 않은 바우처 타입 값: {}", value);
        //프로그램 계속 실행
        throw new IllegalArgumentException("유효하지 않은 Voucher 타입: " + value);
    }
}



