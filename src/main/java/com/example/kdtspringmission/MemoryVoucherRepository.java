package com.example.kdtspringmission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryVoucherRepository implements VoucherRepository {

    private static Map<Long, Voucher> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Long insert(Voucher voucher) {
        store.put(++sequence, voucher);
        return sequence;
    }

    @Override
    public Voucher findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }
}
