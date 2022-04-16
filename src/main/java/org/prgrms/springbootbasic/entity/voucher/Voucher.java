package org.prgrms.springbootbasic.entity.voucher;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;

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

    public Optional<UUID> getCustomerId() {
        return Optional.ofNullable(customerId);
    }

    public void assignCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

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
