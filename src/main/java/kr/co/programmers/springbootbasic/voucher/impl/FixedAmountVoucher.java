package kr.co.programmers.springbootbasic.voucher.impl;


import kr.co.programmers.springbootbasic.exception.NoValidAmountException;
import kr.co.programmers.springbootbasic.voucher.Voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long ZERO_AMOUNT = 0;
    private final UUID voucherId;
    private final long fixedAmount;

    public FixedAmountVoucher(UUID voucherId, long fixedAmount) {
        checkValidFixedAmount(fixedAmount);
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

    private void checkValidFixedAmount(long amount) {
        if (amount <= ZERO_AMOUNT) {
            throw new NoValidAmountException("고정 금액 바우처 생성 금액이 잘못 됐습니다.");
        }
    }
}
