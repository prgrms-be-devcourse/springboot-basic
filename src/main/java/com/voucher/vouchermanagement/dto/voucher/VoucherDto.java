package com.voucher.vouchermanagement.dto.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {

    private final VoucherType voucherType;
    private final UUID id;
    private final long value;
    private final LocalDateTime createdAt;

    public VoucherDto(UUID id, long value, LocalDateTime createdAt, VoucherType voucherType) {
        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
        this.voucherType = voucherType;
    }

    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getValue(),
                voucher.getCreatedAt(),
                VoucherType.getVoucherTypeByName(voucher.getClass().getSimpleName()));
    }

    @Override
    public String toString() {
        return "voucherType=" + voucherType.getTypeName() +
                ", id=" + id +
                ", value=" + value +
                ", createdAt=" + createdAt;
    }
}
