package com.programmers.voucher.testutil;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;

import java.util.UUID;

public class VoucherTestUtil {
    private static final long DEFAULT_AMOUNT = 10;

    public static FixedAmountVoucher createFixedVoucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), DEFAULT_AMOUNT);
    }

    public static PercentDiscountVoucher createPercentVoucher() {
        return new PercentDiscountVoucher(UUID.randomUUID(), DEFAULT_AMOUNT);
    }

    public static VoucherDto createFixedVoucherDto() {
        FixedAmountVoucher fixedVoucher = createFixedVoucher();
        return VoucherDto.from(fixedVoucher);
    }

    public static VoucherDto createPercentVoucherDto() {
        PercentDiscountVoucher percentVoucher = createPercentVoucher();
        return VoucherDto.from(percentVoucher);
    }
}
