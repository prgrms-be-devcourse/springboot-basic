package com.mountain.voucherApp.application.port.out;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherPort {
    List<VoucherEntity> findAll();

    Optional<VoucherEntity> findByPolicyIdAndDiscountAmount(int discountPolicyId, long discountAmount);

    VoucherEntity insert(VoucherEntity voucherEntity);

    Optional<VoucherEntity> findById(UUID voucherId);
}
