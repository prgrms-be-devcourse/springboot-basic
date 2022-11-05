package com.example.springbootbasic.domain.repository;

import com.example.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Map<Long, Voucher> storage = new HashMap<>();
    private static Long sequence;

    @Override
    public Long save(Voucher voucher) {
        storage.put(sequence, voucher);
        return sequence++;
    }

    @Override
    public Optional<Voucher> findVoucherById(Long voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }
}
