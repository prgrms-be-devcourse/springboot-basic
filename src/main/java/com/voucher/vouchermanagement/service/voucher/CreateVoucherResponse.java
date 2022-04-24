package com.voucher.vouchermanagement.service.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateVoucherResponse {
    private final UUID id;
    private final long value;
    private final LocalDateTime createdAt;


    public CreateVoucherResponse(UUID id, long value, LocalDateTime createdAt) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
    }

    public static CreateVoucherResponse of(Voucher voucher) {
        return new CreateVoucherResponse(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt());
    }
}
