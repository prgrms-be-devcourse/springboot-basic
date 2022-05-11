package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    void insert(Voucher voucher);

    List<Voucher> findAll();

    void deleteById(UUID id);

    void deleteAll();

    Optional<Voucher> findById(UUID id);
}
