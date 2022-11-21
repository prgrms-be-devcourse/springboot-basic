package org.prgms.springbootbasic.domain.voucher;

import java.util.UUID;

public record VoucherDTO(UUID voucherId, VoucherType voucherType, long amount) {

    @Override
    public String toString() {
        return "VoucherDTO{" +
                "voucherId=" + voucherId +
                ", voucherType=" + voucherType +
                ", amount=" + amount +
                '}';
    }
}
