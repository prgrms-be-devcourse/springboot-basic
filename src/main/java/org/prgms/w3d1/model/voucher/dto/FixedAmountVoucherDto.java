package org.prgms.w3d1.model.voucher.dto;

import org.prgms.w3d1.model.voucher.FixedAmountVoucher;

import java.util.UUID;

public record FixedAmountVoucherDto(
        UUID voucherId,
        long amount,
        UUID voucherWalletId
)
        implements VoucherDto
{
    static FixedAmountVoucherDto of(FixedAmountVoucher fixedAmountVoucher) {
        return new FixedAmountVoucherDto(
                fixedAmountVoucher.getVoucherId(),
                fixedAmountVoucher.getVoucherValue(),
                fixedAmountVoucher.getVoucherWalletId());
    }

    static FixedAmountVoucher to(FixedAmountVoucherDto fixedAmountVoucherDto) {
        return FixedAmountVoucher.of(
                fixedAmountVoucherDto.voucherId(),
                fixedAmountVoucherDto.amount(),
                fixedAmountVoucherDto.voucherWalletId());
    }
}
