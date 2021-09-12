package com.prgrms.devkdtorder.voucher.dto;

import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class VoucherDto {
    private final UUID voucherId;
    private final long value;
    private final String name;
    private final LocalDateTime createdAt;
    private final VoucherType voucherType;

    public VoucherDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.value = voucher.getValue();
        this.name = voucher.getName();
        this.createdAt = voucher.getCreatedAt();
        this.voucherType = voucher.getVoucherType();
    }

    public Voucher toEntity() {
        return voucherType.createVoucher(voucherId, value, name, createdAt);
    }
}
