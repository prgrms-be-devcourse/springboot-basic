package com.programmers.vouchermanagement.repository.voucher;


import com.programmers.vouchermanagement.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        return voucher;
    }
}
