package org.prgrms.kdt.model.voucher;

import org.prgrms.kdt.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    long getDiscountAmount();

    int getVoucherType();

    LocalDateTime getCreateAt();

    Customer getCustomer();
}
