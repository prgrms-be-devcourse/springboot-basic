package org.prgrms.part1.engine.domain;

import org.prgrms.part1.engine.enumtype.VoucherType;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    Long getValue();

    LocalDateTime getCreatedAt();

    void changeValue(long value);

    void changeOwner(Customer customer);

    void revokeOwner();

    Optional<UUID> getCustomerId();

    String toString();

    String toFileString();
}
