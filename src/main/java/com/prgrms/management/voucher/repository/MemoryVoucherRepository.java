package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .collect(Collectors.toList());
    }
}
