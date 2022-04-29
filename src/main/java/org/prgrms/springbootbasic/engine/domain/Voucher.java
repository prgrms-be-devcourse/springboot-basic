package org.prgrms.springbootbasic.engine.domain;

import org.prgrms.springbootbasic.engine.enumtype.VoucherType;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    Integer getValue();

    LocalDateTime getCreatedAt();

    void changeValue(Integer value);

    void changeOwner(Customer customer);

    void changeOwnerById(UUID customerId);

    void revokeOwner();

    Optional<UUID> getCustomerId();

    String toString();

    String toFileString();
}
