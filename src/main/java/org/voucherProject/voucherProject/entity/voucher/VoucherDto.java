package org.voucherProject.voucherProject.entity.voucher;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VoucherDto {

    private VoucherType voucherType;

    private long amount;

    private VoucherStatus voucherStatus;

    public VoucherDto() {
    }

    public VoucherDto(VoucherType voucherType, long amount, VoucherStatus voucherStatus) {
        this.voucherType = voucherType;
        this.amount = amount;
        this.voucherStatus = voucherStatus;
    }
}
