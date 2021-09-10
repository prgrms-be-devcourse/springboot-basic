package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(UUID voucherId, String name, long amount, VoucherType voucherType, LocalDateTime createdAt) {
        super(voucherId, name, amount, voucherType, createdAt);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (getAmount() / 100);
    }
}
