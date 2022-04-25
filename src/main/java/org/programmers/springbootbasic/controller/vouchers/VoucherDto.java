package org.programmers.springbootbasic.controller.vouchers;

import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import java.util.UUID;

public record VoucherDto(
        UUID voucherId,
        int amount,
        VoucherType type,
        Long memberId) {

    static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getId(), voucher.getAmount(), voucher.getType(), voucher.getMemberId());
    }
}