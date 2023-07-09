package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.model.FixedAmountVoucher;
import org.prgrms.assignment.voucher.model.PercentDiscountVoucher;
import org.prgrms.assignment.voucher.model.Voucher;
import org.prgrms.assignment.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherFactory {


    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, Long benefit, long durationDate) {
        LocalDateTime now = LocalDateTime.now();
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, benefit, now, now.plusDays(durationDate));
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, benefit, now, now.plusDays(durationDate));
            }
        }
        throw new RuntimeException("Failed to create Voucher!");
    }
}
