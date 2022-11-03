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
    private final Long amount; // 할인

    public FixedAmountVoucher(UUID voucherId, Long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount - amount;
    }
}
