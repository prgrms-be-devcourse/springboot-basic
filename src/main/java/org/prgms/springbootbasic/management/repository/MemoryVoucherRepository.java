package org.prgms.springbootbasic.management.repository;

import org.prgms.springbootbasic.management.entity.Voucher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
