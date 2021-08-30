package org.prgrms.kdt.service.dto;

import java.util.UUID;

public class RequestUpdateVoucherDto {

    UUID voucherId;
    long value;

    public RequestUpdateVoucherDto(UUID voucherId, long value) {
        this.voucherId = voucherId;
        this.value = value;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getValue() {
        return value;
    }
}
