package org.prgms.kdt.application.voucher.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;

@Getter
public class VoucherResponseDto {
    UUID voucherId;
    UUID customerId;
    VoucherType voucherType;
    long discountValue;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public VoucherResponseDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.customerId = voucher.getCustomerId();
        this.voucherType = voucher.getVoucherType();
        this.discountValue = voucher.getDiscountValue();
        this.createdAt = voucher.getCreatedAt();
        this.updatedAt = voucher.getUpdatedAt();
    }
}
