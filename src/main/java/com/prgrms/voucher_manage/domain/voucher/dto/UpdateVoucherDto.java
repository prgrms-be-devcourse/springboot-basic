package com.prgrms.voucher_manage.domain.voucher.dto;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;

@Getter @RequiredArgsConstructor
public class UpdateVoucherDto {
    private final UUID id;
    private final VoucherType type;
    private final Long discountAmount;

    public Voucher of(){
        if (type == FIXED) {
            return new FixedAmountVoucher(id,discountAmount);
        }
        return new PercentDiscountVoucher(id,discountAmount);
    }
}
