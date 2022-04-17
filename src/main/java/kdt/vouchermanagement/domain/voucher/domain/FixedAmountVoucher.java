package kdt.vouchermanagement.domain.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final Long amount;

    public FixedAmountVoucher(UUID voucherId, VoucherType voucherType, String discountValue) {
        super(voucherId, voucherType);
        this.amount = Long.valueOf(discountValue);
    }
}
