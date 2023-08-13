package org.promgrammers.voucher.repository;

import org.promgrammers.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    Voucher save(Voucher voucher);

    void deleteAll();

}
