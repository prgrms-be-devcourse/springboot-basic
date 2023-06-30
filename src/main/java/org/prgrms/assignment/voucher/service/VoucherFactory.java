package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.model.FixedAmountVoucher;
import org.prgrms.assignment.voucher.model.PercentDiscountVoucher;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, Long benefit) {
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, benefit);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, benefit);
            }
        }
        throw new RuntimeException("Failed to created Voucher!");
    }
}
