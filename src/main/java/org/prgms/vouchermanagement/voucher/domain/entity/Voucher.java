package org.prgms.vouchermanagement.voucher.domain.entity;

import lombok.Getter;
import org.prgms.vouchermanagement.voucher.VoucherType;

import java.util.UUID;

@Getter
public class Voucher {

    private final UUID voucherId;
    private final long discount;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, long discount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
    }
}
