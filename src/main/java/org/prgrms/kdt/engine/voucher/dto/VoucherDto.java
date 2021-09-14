package org.prgrms.kdt.engine.voucher.dto;

import org.prgrms.kdt.engine.voucher.VoucherType;

public class VoucherDto {
    private VoucherType type;
    private long rate;

    public VoucherType getType() {
        return type;
    }

    public long getRate() {
        return rate;
    }
}
