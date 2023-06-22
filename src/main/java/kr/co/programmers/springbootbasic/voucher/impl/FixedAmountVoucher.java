package kr.co.programmers.springbootbasic.voucher.impl;


import kr.co.programmers.springbootbasic.voucher.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long fixedAmount;

    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return fixedAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("""
                종류 : 고정 금액 바우처
                아이디 : {0}
                할인 가격 : {1}원
                """, voucherId, fixedAmount);
    }
}
