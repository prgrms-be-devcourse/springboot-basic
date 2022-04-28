package com.blessing333.springbasic.domain.voucher.repository;

import com.blessing333.springbasic.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID id);

    List<Voucher> findAll();

    List<Voucher> findByVoucherType(Voucher.VoucherType type);

    void insert(Voucher voucher);

    void update(Voucher voucher);

    void deleteAll();

    void deleteById(UUID id);
}
