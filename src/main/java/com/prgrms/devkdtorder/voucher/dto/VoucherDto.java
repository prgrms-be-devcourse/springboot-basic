package com.prgrms.devkdtorder.voucher.dto;

import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoucherDto {
    private UUID voucherId;
    private long value;
    private String name;
    private LocalDateTime createdAt;
    private VoucherType voucherType;

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
