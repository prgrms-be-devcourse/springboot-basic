package org.prgms.voucher.repository;

import org.prgms.voucher.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    List<Voucher> findAll();

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(long id);
}
