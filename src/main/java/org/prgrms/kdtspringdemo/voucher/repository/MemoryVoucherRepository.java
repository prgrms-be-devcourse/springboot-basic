package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;

import java.util.Optional;
import java.util.UUID;

public class MemoryVoucherRepository implements VoucherRepository{
    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }
}
