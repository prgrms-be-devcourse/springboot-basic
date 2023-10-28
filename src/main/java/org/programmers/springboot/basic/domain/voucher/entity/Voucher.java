package org.programmers.springboot.basic.domain.voucher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;

    public int getVoucherTypeValue() {
        return this.voucherType.getValue();
    }
}
