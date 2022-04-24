package org.programmer.kdtspringboot.voucher;

import java.util.UUID;

public class SearchDeleteVoucherRequest {
    private final UUID voucherId;

    public SearchDeleteVoucherRequest(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
