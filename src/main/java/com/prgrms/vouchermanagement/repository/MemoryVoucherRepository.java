package com.prgrms.vouchermanagement.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        store.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }
}
