package org.prgrms.voucherapp.engine.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public record CustomerVoucherDto(UUID customerId, UUID voucherId, String name,
                                 String status, String voucherType, long discountAmount) {
    @Override
    public String toString() {
        return String.format("id : %36s, %10s님(%-7s) - voucher_id : %36s, type : %-7s, amount : %-7s",
                customerId, name, status == null ? "NONE" : status, voucherId, voucherType, discountAmount);
    }
}
