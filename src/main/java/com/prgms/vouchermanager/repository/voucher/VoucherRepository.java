package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);
    void update(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    void deleteById(UUID id);

    void deleteAll();
}
