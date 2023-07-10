package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.view.VoucherOption;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public abstract class Voucher implements Serializable {
    private static final long EXPIRE_DURATION = 7;   // voucher 기본 유효 기간

    protected UUID code;  // 각 voucher의 고유한 코드
    protected double value;   // voucher의 가치
    protected VoucherOption voucherType;    // voucher 타입
    protected LocalDate expirationDate;    // voucher의 만료일
    protected boolean isActive;   // voucher의 활성화 상태
    protected UUID customerId;    // 할당 받은 고객

    Voucher() {
        this.code = UUID.randomUUID();
        this.expirationDate = setExpirationDate();
        this.isActive = true;
    }

    private LocalDate setExpirationDate() {
        LocalDate date = LocalDate.now();
        date.plusDays(EXPIRE_DURATION);

        return date;
    }
}
