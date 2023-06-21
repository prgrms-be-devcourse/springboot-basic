package org.prgms.vouchermanagement.repository;

import org.prgms.vouchermanagement.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);
}
