package com.mountain.voucherapp.dao.voucher;

import com.mountain.voucherapp.model.VoucherEntity;
import com.mountain.voucherapp.model.enums.DiscountPolicy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    List<VoucherEntity> findAll();

    Optional<VoucherEntity> findByDiscountPolicyAndAmount(DiscountPolicy discountPolicy, long discountAmount);

    VoucherEntity insert(VoucherEntity voucherEntity);

    Optional<VoucherEntity> findById(UUID voucherId);

    void deleteById(UUID voucherId);
}
