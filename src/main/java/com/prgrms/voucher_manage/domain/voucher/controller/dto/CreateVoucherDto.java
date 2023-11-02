package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

public record CreateVoucherDto(
    VoucherType type,
    Long discountAmount
)
{
    public Voucher of() {
        if (type == FIXED) {
            return new FixedAmountVoucher(discountAmount);
        }
        return new PercentDiscountVoucher(discountAmount);
    }
}
