package com.wonu606.vouchermanager.repository;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    void deleteById(UUID id);

    void deleteAll();
}
