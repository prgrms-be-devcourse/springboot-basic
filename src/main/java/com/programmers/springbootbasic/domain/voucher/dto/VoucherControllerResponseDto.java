package com.programmers.springbootbasic.domain.voucher.dto;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VoucherControllerResponseDto {
    private final UUID voucherId;
    private final long value;
    private final int voucherType;
    private final LocalDate createdAt;

    public static VoucherControllerResponseDto of(Voucher voucher) {
        return new VoucherControllerResponseDto(
                voucher.getVoucherId(),
                voucher.getValue(),
                VoucherType.predictVoucherType(voucher),
                voucher.getCreatedAt());
    }
}
