package org.prgrms.kdt.service.dto;

import org.prgrms.kdt.domain.voucher.VoucherType;

public class RequestCreatVoucherDto {

    private VoucherType type;
    private long amount;

    public RequestCreatVoucherDto(VoucherType type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public VoucherType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
