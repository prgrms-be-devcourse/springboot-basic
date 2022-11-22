package org.prgrms.springorder.domain.voucher.api.response;

import java.util.UUID;

public class VoucherCreateResponse {

    private final UUID voucherId;

    public VoucherCreateResponse(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
