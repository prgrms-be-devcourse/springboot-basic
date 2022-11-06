package com.example.springbootbasic.repository;

import com.example.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Map<Long, Voucher> storage = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Long save(Long voucherId, Voucher voucher) {
        storage.put(voucherId, voucher);
        return voucherId;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new ArrayList<>(
                storage.values()
                        .stream()
                        .toList());
    }

    @Override
    public Long getSequence() {
        return sequence++;
    }
}
