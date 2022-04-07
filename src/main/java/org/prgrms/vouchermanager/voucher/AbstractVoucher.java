package org.prgrms.vouchermanager.voucher;

import java.util.UUID;

public abstract class AbstractVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType type;

    protected AbstractVoucher(UUID voucherId, VoucherType type) {
        this.voucherId = voucherId;
        this.type = type;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return type;
    }

}
