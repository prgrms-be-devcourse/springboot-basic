package org.promgrammers.voucher.repository;

import org.promgrammers.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {

    Optional<Voucher> findById(long id);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);

    void deleteAll();

}
