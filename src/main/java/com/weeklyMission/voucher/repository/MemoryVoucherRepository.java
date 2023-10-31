package com.weeklyMission.voucher.repository;

import com.weeklyMission.voucher.domain.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<String, Voucher> storage;

    public MemoryVoucherRepository(){
        storage = new ConcurrentHashMap<>();
    }

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Voucher> findById(String id) {
        return Optional.of(storage.get(id));
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
