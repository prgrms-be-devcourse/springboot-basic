package org.programmers.kdtspring.entity.voucher;

import org.programmers.kdtspring.entity.user.Customer;

import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private UUID customerId;

    public Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public Voucher(UUID voucherId, UUID customerId) {
        this(voucherId);
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void belongToCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

    public abstract int getDiscount();

    public abstract String getVoucherType();

}