package com.prgms.kdtspringorder.adapter.repositoryImpl;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherRepository;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();   // thread-safety

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return storage;
    }
}
