package org.prgms.voucheradmin.domain.voucherwallet.dto;

import java.util.UUID;

public class VoucherWalletReqDto {
    private UUID voucherId;

    public VoucherWalletReqDto(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
