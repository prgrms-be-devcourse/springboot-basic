package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local-memory")
public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        if (!voucherMap.containsKey(voucher.getVoucherId())) {
            voucherMap.put(voucher.getVoucherId(), voucher);
            return Optional.of(voucher);
        }
        return Optional.empty();
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        return voucherMap;
    }
}
