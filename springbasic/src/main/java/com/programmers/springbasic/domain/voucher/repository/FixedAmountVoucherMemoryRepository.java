package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class FixedAmountVoucherMemoryRepository implements VoucherRepository<FixedAmountVoucher, UUID> {
    private final Map<UUID, FixedAmountVoucher> fixedAmountVouchers = new HashMap<>();

    @Override
    public void save(FixedAmountVoucher voucher) {
        UUID voucherId = voucher.getCode();

        fixedAmountVouchers.put(voucherId, voucher);
    }

    @Override
    public List<FixedAmountVoucher> findAll() {
        return fixedAmountVouchers.values()
                .stream()
                .collect(Collectors.toList());
    }
}
