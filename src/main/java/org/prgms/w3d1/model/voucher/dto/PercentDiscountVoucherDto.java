package org.prgms.w3d1.model.voucher.dto;

import org.prgms.w3d1.model.voucher.PercentDiscountVoucher;

import java.util.UUID;

public record PercentDiscountVoucherDto(
        UUID voucherId,
        long percent,
        UUID voucherWalletId
) implements VoucherDto
{
    static PercentDiscountVoucherDto of(PercentDiscountVoucher percentDiscountVoucher) {
        return new PercentDiscountVoucherDto(
                percentDiscountVoucher.getVoucherId(),
                percentDiscountVoucher.getVoucherValue(),
                percentDiscountVoucher.getVoucherWalletId());
    }

    static PercentDiscountVoucher to(PercentDiscountVoucherDto percentDiscountVoucherDto) {
        return PercentDiscountVoucher.of(
                percentDiscountVoucherDto.voucherId(),
                percentDiscountVoucherDto.percent(),
                percentDiscountVoucherDto.voucherWalletId());
    }
}
