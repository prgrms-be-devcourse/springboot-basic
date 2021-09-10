package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByName(String name) {
        return new ArrayList<>();
    }

    @Override
    public List<Voucher> findByType(String type) {
        return new ArrayList<>();
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }
}