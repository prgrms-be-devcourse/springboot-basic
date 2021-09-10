package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.domain.customer.Customer;

public interface Voucher {

    Long getVoucherId();

    long getAmount();

    long discount(long beforeDiscount);

    Long getCustomerId();

    VoucherType getType();

    void allocateCustomer(Customer customer);
}
