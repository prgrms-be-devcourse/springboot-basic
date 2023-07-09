package org.prgrms.assignment.voucher.service;

import org.prgrms.assignment.voucher.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class VoucherFactory {

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, Long benefit, long durationDate) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireDate = now.plusDays(durationDate);
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, benefit, now, expireDate);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, benefit, now, expireDate);
            }
        }
        throw new RuntimeException("Failed to create Voucher!");
    }
}
