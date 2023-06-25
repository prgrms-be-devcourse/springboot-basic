package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class PercentDiscountVoucherMemoryRepository implements VoucherRepository<PercentDiscountVoucher, UUID> {
    private final Map<UUID, PercentDiscountVoucher> percentDiscountVouchers = new HashMap<>();

    @Override
    public void save(PercentDiscountVoucher voucher) {
        UUID voucherId = voucher.getCode();

        percentDiscountVouchers.put(voucherId, voucher);
    }

    @Override
    public List<PercentDiscountVoucher> findAll() {
        return percentDiscountVouchers.values()
                .stream()
                .collect(Collectors.toList());
    }
}
