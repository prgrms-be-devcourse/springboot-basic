package org.programmers.springbootbasic.application.voucher.repository;

import org.programmers.springbootbasic.core.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.application.voucher.model.Voucher;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID voucherId);

    Voucher insert(Voucher voucher) throws DuplicateObjectKeyException;

    List<Voucher> findAll();

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findByCreatedAt();

    int getCountByVoucherId(UUID voucherId);
}

