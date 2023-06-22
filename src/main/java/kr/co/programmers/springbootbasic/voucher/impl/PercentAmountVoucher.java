package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.voucher.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percentAmount;

    public PercentAmountVoucher(UUID voucherId, long percentAmount) {
        this.voucherId = voucherId;
        this.percentAmount = percentAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return percentAmount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("""
                종류 : 고정 할인률 바우처
                아이디 : {0}
                할인률 : {1}%
                """, voucherId, percentAmount);
    }
}
