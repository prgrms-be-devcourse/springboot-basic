package com.programmers.voucher.testutil;

import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;

import java.util.UUID;

public class VoucherTestUtil {
    public static Voucher createFixedVoucher(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    public static Voucher createPercentVoucher(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }

    public static VoucherDto createFixedVoucherDto(UUID voucherId, long amount) {
        Voucher fixedVoucher = createFixedVoucher(voucherId, amount);
        return VoucherDto.from(fixedVoucher);
    }

    public static VoucherDto createPercentVoucherDto(UUID voucherId, long amount) {
        Voucher percentVoucher = createPercentVoucher(voucherId, amount);
        return VoucherDto.from(percentVoucher);
    }
}
