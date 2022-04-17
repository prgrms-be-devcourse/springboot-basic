package com.mountain.voucherApp.voucher.repository;

import com.mountain.voucherApp.voucher.VoucherEntity;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    List<VoucherEntity> findAll();
    Optional<VoucherEntity> findByPolicyIdAndDiscountAmount(int discountPolicyId, long discountAmount);
    VoucherEntity insert(VoucherEntity voucherEntity);
}
