package org.prgms.kdtspringvoucher.voucher.domain;

import org.prgms.kdtspringvoucher.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    UUID getCustomerId();

    Long getAmount();

    VoucherType getVoucherType();

    LocalDateTime getCreatedAt();

    void changeAmount(Long amount);

    void assignVoucherToCustomer(UUID customerId);
}
