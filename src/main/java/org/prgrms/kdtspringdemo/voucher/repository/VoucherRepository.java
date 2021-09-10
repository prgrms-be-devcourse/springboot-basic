package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
    Optional<Voucher> findById(UUID voucherId);
    List<Voucher> findByName(String name);
    List<Voucher> findByType(String email);

    Voucher update(Voucher voucher);

    void deleteAll();

    int count();
}
