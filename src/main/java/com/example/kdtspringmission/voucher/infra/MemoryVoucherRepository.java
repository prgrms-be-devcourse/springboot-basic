package com.example.kdtspringmission.voucher.infra;

import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.domain.VoucherRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@Repository
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
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Voucher> findByOwnerId(UUID ownerId) {
        return null;
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}
