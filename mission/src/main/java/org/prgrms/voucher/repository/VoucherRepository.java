package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(Long voucherId);

    void deleteById(Long voucherId);
}
