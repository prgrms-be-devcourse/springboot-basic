package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.util.UUID;

public class VoucherFactory {
    public VoucherFactory() {};

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, Long benefit) {
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, benefit);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, benefit);
            }
        }
        return null;
    }
}
