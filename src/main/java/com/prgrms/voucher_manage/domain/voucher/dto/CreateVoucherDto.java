package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.*;

public final class CreateVoucherDto {
    private final VoucherType voucherType;
    private final Long discountAmount;

    public CreateVoucherDto(VoucherType voucherType, Long discountAmount) {
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public Voucher of() {
        if (voucherType == FIXED) {
            return new FixedAmountVoucher(discountAmount);
        }
        return new PercentDiscountVoucher(discountAmount);
    }
}
