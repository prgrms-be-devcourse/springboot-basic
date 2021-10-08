package org.prgrms.kdt.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(final UUID voucherId, final long amount) {
        if (amount <= 0 || amount > 1000000) throw new IllegalArgumentException("할인 금액은 0원 초과, 1,000,000원 이하이어야 합니다.");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherDiscount() {
        return amount;
    }

    @Override
    public String getVoucherType() {
        return "FixedAmountVoucher";
    }

    public long discount(final long beforeDiscount) {
        return beforeDiscount - amount < 0 ? 0 : beforeDiscount - amount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("FixedAmountVoucher( {0}원 ), voucher_id = {1}", amount, voucherId.toString());
    }
}
