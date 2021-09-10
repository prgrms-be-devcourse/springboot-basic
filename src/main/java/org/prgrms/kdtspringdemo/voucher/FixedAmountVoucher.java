package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(UUID voucherId, String name, long amount, VoucherType voucherType, LocalDateTime createdAt) {
        super(voucherId, name, amount, voucherType, createdAt);
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount - getAmount();
    }
}
