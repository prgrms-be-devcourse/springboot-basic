package com.prgrms.voucher_manager.repository;

import com.prgrms.voucher_manager.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherID(), voucher);

    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}

