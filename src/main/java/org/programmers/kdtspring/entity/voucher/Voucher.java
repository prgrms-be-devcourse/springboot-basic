package org.programmers.kdtspring.entity.voucher;

import org.programmers.kdtspring.entity.user.Customer;

public abstract class Voucher {

    private final Long voucherId;
    private Long customerId;

    public Voucher(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Voucher(Long voucherId, Long customerId) {
        this(voucherId);
        this.customerId = customerId;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void belongToCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

    public abstract long discount(long beforeDiscount);

    public abstract int getDiscount();

    public abstract String getVoucherType();

}
