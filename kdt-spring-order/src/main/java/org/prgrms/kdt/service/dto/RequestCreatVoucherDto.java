package org.prgrms.kdt.service.dto;

import org.prgrms.kdt.domain.voucher.VoucherType;

public class RequestCreatVoucherDto {

    private VoucherType type;
    private long value;

    public RequestCreatVoucherDto(VoucherType type, long value) {
        this.type = type;
        this.value = value;
    }

    public VoucherType getType() {
        return type;
    }

    public long getValue() {
        return value;
    }
}
