package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;

import java.util.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public void insert(Voucher voucher) {
        this.store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(this.store.values());
    }

    @Override
    public void deleteAll() {
        this.store.clear();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.of(this.store.get(id));
    }
}
