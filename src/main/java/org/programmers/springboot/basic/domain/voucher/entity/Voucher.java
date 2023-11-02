package org.programmers.springboot.basic.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;

    public int getVoucherTypeValue() {
        return this.voucherType.getValue();
    }
}
