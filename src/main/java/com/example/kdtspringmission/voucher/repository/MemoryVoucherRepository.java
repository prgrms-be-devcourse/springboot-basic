package com.example.kdtspringmission.voucher.repository;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
//@Profile({"local", "test"})
public class MemoryVoucherRepository implements VoucherRepository {

    private static Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public UUID insert(Voucher voucher) {
        UUID id = voucher.getId();
        store.put(id, voucher);
        return id;
    }

    @Override
    public Voucher findById(UUID id) {
        return store.get(id);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
    }
}
