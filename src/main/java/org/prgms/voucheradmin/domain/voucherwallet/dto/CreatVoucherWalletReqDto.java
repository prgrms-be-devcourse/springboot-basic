package org.prgms.voucheradmin.domain.voucherwallet.dto;

import java.util.UUID;

public class CreatVoucherWalletReqDto {
    private UUID customerId;
    private UUID voucherId;

    public CreatVoucherWalletReqDto(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
