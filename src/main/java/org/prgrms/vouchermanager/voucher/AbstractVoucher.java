package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public abstract class AbstractVoucher implements Voucher {

    private final UUID voucherId;

    protected AbstractVoucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
