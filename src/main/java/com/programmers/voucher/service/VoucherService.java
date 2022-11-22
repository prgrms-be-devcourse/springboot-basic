package com.programmers.voucher.service;

import com.programmers.voucher.domain.VoucherEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherService {
    void createVoucher(String type, int discount);

    List<VoucherEntity> getAllVouchers();

    List<VoucherEntity> findByType(String type);

    Optional<VoucherEntity> findById(UUID id);

    void deleteAll();

}
