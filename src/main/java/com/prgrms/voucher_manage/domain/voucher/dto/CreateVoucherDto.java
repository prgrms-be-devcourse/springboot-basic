package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.*;

public record CreateVoucherDto(VoucherType voucherType, Long discountAmount) {
    public Voucher of() {
        if (voucherType == FIXED){
            return new FixedAmountVoucher(discountAmount);
        }
        return new PercentDiscountVoucher(discountAmount);
    }
}
