package org.prgrms.springbootbasic.entity.voucher;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.customer.Customer;

public abstract class Voucher implements Serializable {

    private final UUID voucherId;
    private UUID customerId;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    protected Voucher(UUID voucherId, UUID customerId) {
        this(voucherId);
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void assignCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

    public abstract boolean isFixed();

    public abstract boolean isPercent();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Voucher voucher = (Voucher) o;
        return getVoucherId().equals(voucher.getVoucherId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVoucherId());
    }
}
