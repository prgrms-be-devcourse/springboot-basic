package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher) throws DuplicateObjectKeyException;

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);

    int getCountByVoucherId(UUID voucherId);
}

