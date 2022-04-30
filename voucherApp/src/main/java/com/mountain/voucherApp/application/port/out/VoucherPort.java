package com.mountain.voucherApp.application.port.out;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherPort {
    List<VoucherEntity> findAll();

    Optional<VoucherEntity> findByDiscountPolicyAndAmount(DiscountPolicy discountPolicy, long discountAmount);

    VoucherEntity insert(VoucherEntity voucherEntity);

    Optional<VoucherEntity> findById(UUID voucherId);

    void deleteById(UUID voucherId);
}
