package org.voucherProject.voucherProject.entity.voucher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class VoucherDto {

    private UUID voucherId;

    private VoucherType voucherType;

    private long amount;

    private VoucherStatus voucherStatus;

    private UUID customerId;

    public VoucherDto(VoucherType voucherType, long amount, VoucherStatus voucherStatus) {
        this.voucherType = voucherType;
        this.amount = amount;
        this.voucherStatus = voucherStatus;
    }
}
