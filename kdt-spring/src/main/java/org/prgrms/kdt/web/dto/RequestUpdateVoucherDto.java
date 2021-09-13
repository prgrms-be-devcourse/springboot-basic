package org.prgrms.kdt.web.dto;

import java.util.UUID;

public class RequestUpdateVoucherDto {
    private UUID voucherId;
    private Long voucherValue;

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public Long getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherValue(Long voucherValue) {
        this.voucherValue = voucherValue;
    }
}
