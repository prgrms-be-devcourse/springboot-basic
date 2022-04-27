package org.programmers.springbootbasic.controller.vouchers;

import lombok.Getter;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record VoucherDto(
        @NotNull
        UUID voucherId,
        @NotNull
        Integer amount,
        @NotNull
        VoucherType type,
        Long memberId) {

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getId(), voucher.getAmount(), voucher.getType(), voucher.getMemberId());
    }
}