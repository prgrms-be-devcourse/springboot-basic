package org.prgrms.voucher.models;

import java.time.LocalDateTime;

public class PercentDiscountVoucher extends Voucher{


    public PercentDiscountVoucher(long percent, VoucherType voucherType) {

        super(percent, voucherType);
        validate(percent);
    }

    public PercentDiscountVoucher(Long voucherId, long percent, VoucherType voucherType,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {

        super(voucherId, percent, voucherType, createdAt, updatedAt);
    }

    @Override
    public long discount() {

        return 0;
    }

    private void validate(long percent) {

        if (percent > 100 || percent < 0) {
            throw new IllegalArgumentException("bad discountValue");
        }
    }
}