package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import java.util.UUID;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;


public record UpdateVoucherReq(
    UUID id,
    VoucherType type,
    Long discountAmount
)
{
    public Voucher of() {
        if (type == FIXED) {
            return new FixedAmountVoucher(id, discountAmount);
        }
        return new PercentDiscountVoucher(id, discountAmount);
    }
}
