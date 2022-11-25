package org.programmers.springbootbasic.domain.voucher.repository;

import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(String voucherType);

    void update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID voucherId);
}
