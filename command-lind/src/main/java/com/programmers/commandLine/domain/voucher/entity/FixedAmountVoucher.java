package com.programmers.commandLine.domain.voucher.entity;

import java.util.UUID;
/**
 *
 *  FixedAmountVoucher의 설명을 여기에 작성한다.
 *  고정적인 할인금액을 결정하는 바우처 입니다.
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/03
 *
**/
public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final Integer amount; // 할인
    private final String type = "fixedAmountVoucher";

    public FixedAmountVoucher(UUID voucherId, Integer amount) {
        this.voucherId = voucherId;
        this.amount = amount;

    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Integer discount(Integer beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getDiscount() {
        return this.amount.toString() + "$";
    }
}
