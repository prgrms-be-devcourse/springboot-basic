package org.programmers.kdtspring.entity.voucher;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {


    private final int amount;
    private final VoucherType voucherType = VoucherType.FixedAmountVoucher;

    public FixedAmountVoucher(UUID voucherId, int amount) {
        super(voucherId);
        this.amount = amount;
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, int amount) {
        super(voucherId, customerId);
        this.amount = amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public int getDiscount() {
        return this.amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
