package org.prgrms.kdt.web.dto;

import java.util.UUID;

public class RequestDeleteVoucherDto {
    private UUID voucherId;

    public RequestDeleteVoucherDto(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }
}
