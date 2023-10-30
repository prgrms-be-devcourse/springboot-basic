package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    void save(Voucher voucher);

    void saveAll(List<Voucher> vouchers);

    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    void update(Voucher voucher);

    void deleteById(UUID id);

    void deleteAll();
}
