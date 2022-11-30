package com.prgrms.springbootbasic.voucher.factory;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile("prod | test")
@Component
public class PercentVoucherFactory implements VoucherFactory {
    @Override
    public Voucher mapToVoucher(UUID id, int discountAmount) {
        return new PercentVoucher(id, discountAmount, VoucherType.PERCENT);
    }

    @Override
    public Voucher createVoucher(int discountAmount) {
        return new PercentVoucher(discountAmount, VoucherType.PERCENT);
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT;
    }
}
