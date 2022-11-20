package org.programmers.program.voucher.repository;

import org.programmers.program.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);
    Optional<Voucher> findById(UUID id);
    List<Voucher> findAll();
    void deleteAll();
    int count();
}
