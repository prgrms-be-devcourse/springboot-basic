package com.programmers.springbasic.domain.voucher.entity;

import com.programmers.springbasic.domain.voucher.model.VoucherType;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public abstract class Voucher implements Serializable { // 바우처는 할인만 해주는 친구..(구현체가 구체적으로 어떻게 할인을 할 것인지 결정)
    protected UUID code;  // 각 voucher의 고유한 코드
    protected double value;   // voucher의 가치
    protected LocalDate expirationDate;    // voucher의 만료일
    protected boolean isActive;   // voucher의 활성화 상태
    protected UUID customerId;    // 할당 받은 고객

    public abstract double discount(double inputAmount);

    public abstract void updateValue(double value);
    public abstract VoucherType getVoucherType();
}
