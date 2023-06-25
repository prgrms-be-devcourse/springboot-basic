package com.prgrms.springbootbasic.repository;

import com.prgrms.springbootbasic.domain.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID,Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getAllVouchersList() {
        return new ArrayList<>(storage.values());
    }
}
