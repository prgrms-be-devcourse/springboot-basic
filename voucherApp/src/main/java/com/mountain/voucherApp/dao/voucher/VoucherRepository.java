package com.mountain.voucherApp.dao.voucher;

import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.model.enums.DiscountPolicy;

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
