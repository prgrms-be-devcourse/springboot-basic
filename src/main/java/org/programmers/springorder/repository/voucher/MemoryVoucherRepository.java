package org.programmers.springorder.repository.voucher;

import org.programmers.springorder.model.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    public UUID save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

}
