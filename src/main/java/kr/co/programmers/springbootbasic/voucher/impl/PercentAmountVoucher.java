package kr.co.programmers.springbootbasic.voucher.impl;

import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private static final long ZERO_PERCENT = 0;
    private static final long ONE_HUNDRED_PERCENT = 100;
    private final UUID voucherId;
    private final long percentAmount;

    public PercentAmountVoucher(UUID voucherId, long percentAmount) {
        checkValidPercentAmount(percentAmount);
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

    private void checkValidPercentAmount(long amount) {
        if (amount <= ZERO_PERCENT || amount >= ONE_HUNDRED_PERCENT) {
            throw new NoValidAmountException("고정 할인률 바우처 생성 할인률이 잘못 됐습니다.");
        }
    }
}
