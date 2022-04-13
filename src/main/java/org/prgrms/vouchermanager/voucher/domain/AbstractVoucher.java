package org.prgrms.vouchermanager.voucher.domain;

import java.util.UUID;

public abstract class AbstractVoucher implements Voucher {

    private final UUID voucherId = UUID.randomUUID();
    private final VoucherType type;

    public AbstractVoucher(VoucherType type) {
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
