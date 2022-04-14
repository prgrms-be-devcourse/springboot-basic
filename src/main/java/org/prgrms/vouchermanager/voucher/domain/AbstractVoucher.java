package org.prgrms.vouchermanager.voucher.domain;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractVoucher that = (AbstractVoucher) o;
        return Objects.equals(voucherId, that.voucherId) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, type);
    }
}
