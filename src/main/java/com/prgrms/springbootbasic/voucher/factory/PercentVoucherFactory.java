package com.prgrms.springbootbasic.voucher.factory;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

@Component
public class PercentVoucherFactory implements VoucherFactory {
    @Override
    public Voucher createVoucher(VoucherType VoucherType, int discountAmount) {
        return new PercentVoucher(VoucherType.getInputValue(), discountAmount);
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT;
    }
}
