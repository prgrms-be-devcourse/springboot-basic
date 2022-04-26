package com.prgms.management.voucher.dto;

import com.prgms.management.common.exception.SaveFailException;
import com.prgms.management.voucher.model.FixedAmountVoucher;
import com.prgms.management.voucher.model.PercentDiscountVoucher;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;

public record VoucherRequest(
    Integer figure,
    String type,
    String name
) {
    public Voucher toVoucher() {
        if (VoucherType.valueOf(type.toUpperCase()) == VoucherType.FIXED) {
            return new FixedAmountVoucher(name, figure);
        } else if (VoucherType.valueOf(type.toUpperCase()) == VoucherType.PERCENT) {
            return new PercentDiscountVoucher(name, figure);
        } else {
            throw new SaveFailException("바우처 등록에 실패하였습니다.");
        }
    }
}
