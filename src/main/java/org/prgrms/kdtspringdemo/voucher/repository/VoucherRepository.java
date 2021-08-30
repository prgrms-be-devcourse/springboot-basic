package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.Voucher;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface VoucherRepository {
    public Optional<Voucher> findById(UUID voucherId);

    public Voucher insert(Voucher voucher);

    public Stream<Voucher> findAll();
}
