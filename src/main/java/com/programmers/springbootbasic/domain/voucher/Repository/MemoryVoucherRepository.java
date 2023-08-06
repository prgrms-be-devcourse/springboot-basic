package com.programmers.springbootbasic.domain.voucher.Repository;

import com.programmers.springbootbasic.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final String ALREADY_EXISTS = "이미 해당 아이디의 바우처가 존재합니다.";
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public UUID save(Voucher voucher) {
        if (storage.containsKey(voucher.getVoucherId())) {
            throw new IllegalStateException(ALREADY_EXISTS);
        }
        storage.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public long countTotalVoucher() {
        return storage.size();
    }
}
