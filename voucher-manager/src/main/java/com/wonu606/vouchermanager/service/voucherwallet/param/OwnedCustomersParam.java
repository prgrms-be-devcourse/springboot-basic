package com.wonu606.vouchermanager.service.voucherwallet.param;

import java.util.UUID;

public class OwnedCustomersParam {

    private final UUID voucherUuid;

    public OwnedCustomersParam(UUID voucherUuid) {
        this.voucherUuid = voucherUuid;
    }

    public UUID getVoucherUuid() {
        return voucherUuid;
    }
}
