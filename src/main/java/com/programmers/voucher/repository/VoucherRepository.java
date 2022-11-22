package com.programmers.voucher.repository;

import com.programmers.voucher.domain.VoucherEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    VoucherEntity insert(VoucherEntity voucherEntity);
    VoucherEntity update(VoucherEntity voucherEntity);
    Optional<VoucherEntity> findByID(UUID voucherID);
    List<VoucherEntity> findByType(String type);
    List<VoucherEntity> findAll();
    void deleteAll();
}
