package kr.co.programmers.school.voucher.domain.voucher.domain;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final int percent;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String printVoucher() {
        return MessageFormat.format("[Percent Voucher - {0}] {1} % discount", voucherId, percent);
    }
}