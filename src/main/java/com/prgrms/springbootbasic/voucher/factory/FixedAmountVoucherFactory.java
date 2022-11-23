package com.prgrms.springbootbasic.voucher.factory;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile("prod | test")
@Component
public class FixedAmountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher mapToVoucher(UUID id, int discountAmount) {
        return new FixedAmountVoucher(id, discountAmount, VoucherType.FIXED_AMOUNT);
    }

    @Override
    public Voucher createVoucher(int discountAmount) {
        return new FixedAmountVoucher(discountAmount, VoucherType.FIXED_AMOUNT);
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED_AMOUNT;
    }
}
