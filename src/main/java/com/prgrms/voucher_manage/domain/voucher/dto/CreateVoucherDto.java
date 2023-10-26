package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

public final class CreateVoucherDto {
    private final VoucherType type;
    private final Long discountAmount;

    public CreateVoucherDto(VoucherType type, Long discountAmount) {
        this.type = type;
        this.discountAmount = discountAmount;
    }

    public Voucher of() {
        if (type == FIXED) {
            return new FixedAmountVoucher(discountAmount);
        }
        return new PercentDiscountVoucher(discountAmount);
    }
}
