package org.prgms.management.repository;

import org.prgms.management.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local-memory")
public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public boolean save(Voucher voucher) {
        if (!voucherMap.containsKey(voucher.getVoucherId())) {
            voucherMap.put(voucher.getVoucherId(), voucher);
            return true;
        }
        return false;
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        return voucherMap;
    }
}
