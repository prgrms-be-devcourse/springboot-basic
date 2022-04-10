package org.prgms.management.repository;

import org.prgms.management.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        if (!voucherMap.containsKey(voucher.getVoucherId()))
            voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        return voucherMap;
    }
}
