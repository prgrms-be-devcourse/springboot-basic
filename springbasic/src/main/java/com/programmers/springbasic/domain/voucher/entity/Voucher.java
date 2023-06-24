package com.programmers.springbasic.domain.voucher.entity;

import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
public abstract class Voucher {
    private static final int EXPIRE_DURATION = 7;   // voucher 기본 유효 기간

    protected UUID code;  // 각 voucher의 고유한 코드
    protected double value;   // voucher의 가치
    protected Date expirationDate;    // voucher의 만료일
    protected boolean isActive;   // voucher의 활성화 상태

    Voucher() {
        this.code = UUID.randomUUID();
        this.expirationDate = getExpirationDate();
        this.isActive = true;
    }

    public abstract String getInfo();

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, EXPIRE_DURATION);

        return calendar.getTime();
    }
}
