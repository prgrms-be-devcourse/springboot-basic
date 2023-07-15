package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("memory")
class MemoryVoucherRepository extends AbstractVoucherRepository {
    private final Map<UUID, Voucher> memoryStorage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        memoryStorage.put(voucher.id(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(memoryStorage.values());
    }
}
