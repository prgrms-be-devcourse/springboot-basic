package org.programmers.springbootbasic.web.controller.vouchers;

import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

public record VoucherDto(
        @NotNull
        UUID voucherId,
        @NotNull
        Integer amount,
        @NotNull
        VoucherType type,
        @NotNull
        Timestamp registeredAt,
        Long memberId) {

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getId(), voucher.getAmount(), voucher.getType(), voucher.getRegisteredAt(), voucher.getMemberId());
    }
}