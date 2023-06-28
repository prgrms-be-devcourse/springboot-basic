package com.programmers.application.repository;

import com.programmers.application.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }
}
