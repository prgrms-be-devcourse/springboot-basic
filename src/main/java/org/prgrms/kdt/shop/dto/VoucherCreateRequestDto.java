package org.prgrms.kdt.shop.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherCreateRequestDto {

    private final String voucherType;
    private final int voucherAmount;

    public VoucherCreateRequestDto(String voucherType, int voucherAmount) {
        this.voucherType = voucherType;
        this.voucherAmount = voucherAmount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public int getVoucherAmount() {
        return voucherAmount;
    }

    public VoucherCreateServiceDto toServiceDto() {
        return new VoucherCreateServiceDto(UUID.randomUUID(), voucherAmount, voucherType,
            LocalDateTime.now());
    }
}
