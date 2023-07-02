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


    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, Long benefit) {
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, benefit, LocalDateTime.now());
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, benefit, LocalDateTime.now());
            }
        }
        throw new RuntimeException("Failed to create Voucher!");
    }

    // for Mapper
    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, LocalDateTime createdAt, Long benefit) {
        switch (voucherType) {
            case FIXED -> {
                return new FixedAmountVoucher(voucherId, benefit, createdAt);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(voucherId, benefit, createdAt);
            }
        }
        throw new RuntimeException("Failed to created Voucher!");
    }
}
