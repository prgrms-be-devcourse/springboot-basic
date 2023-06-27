package com.wonu606.vouchermanager.repository;

import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.domain.Vouchers;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void save(Voucher voucher);

    Optional<Voucher> findById(UUID id);

    Vouchers findAll();

    void deleteById(UUID id);

    void deleteAll();
}
