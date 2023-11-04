package com.prgrms.voucher_manage.domain.voucher.controller.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

public record CreateVoucherReq(
    @NotBlank(message = "바우처 유형을 입력해주세요.")
    VoucherType type,
    @NotNull(message="할인 금액을 입력해주세요.")
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
