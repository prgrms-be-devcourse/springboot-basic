package org.prgrms.kdtbespring.voucher;

import java.util.UUID;

public enum VoucherType {
    PercentDiscountVoucher{
        @Override
        Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long percent) {
            Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, percent);
            voucherRepository.insert(percentDiscountVoucher);
            return percentDiscountVoucher;
        }
    },
    FixedAmountVoucher{
        @Override
        Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long amount) {
            Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, amount);
            voucherRepository.insert(fixedAmountVoucher);
            return fixedAmountVoucher;
        }
    };

    abstract Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long value);
}
