package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

public abstract class AbstractVoucher implements Voucher {

    private final UUID voucherId;
    private final VoucherType type;

    public AbstractVoucher(UUID voucherId, VoucherType type) {
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
