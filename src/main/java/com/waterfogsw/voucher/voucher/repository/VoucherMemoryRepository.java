package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherStore = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        voucherStore.put(voucher.getId(), voucher);
        return voucher;
    }
}
