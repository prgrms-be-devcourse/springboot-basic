package org.prgrms.kdt.web.dto;

import java.util.UUID;

public class RequestUpdateVoucherDto {
    private UUID voucherId;
    private Long value;

    public RequestUpdateVoucherDto(UUID voucherId, Long value) {
        this.voucherId = voucherId;
        this.value = value;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
