package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.pattern.VoucherVisitor;

public class VoucherDtoConverter implements VoucherVisitor {
    private VoucherDto voucherDto;

    public VoucherDto convert(Voucher voucher) {
        voucher.accept(this);
        return voucherDto;
    }

    @Override
    public void visit(FixedAmountVoucher voucher) {
        voucherDto = new VoucherDto(
                voucher.getVoucherId(),
                VoucherType.FIXED_AMOUNT,
                voucher.getAmount());
    }

    @Override
    public void visit(PercentDiscountVoucher voucher) {
        voucherDto = new VoucherDto(
                voucher.getVoucherId(),
                VoucherType.PERCENT,
                voucher.getPercent()
        );
    }
}
