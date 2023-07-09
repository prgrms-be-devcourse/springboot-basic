package com.programmers.voucher.repository;

import com.programmers.exception.NotFoundException;
import com.programmers.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        if (!storage.containsKey(voucher.getVoucherId())) {
            storage.put(voucher.getVoucherId(), voucher);
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>(storage.values());
        return Collections.unmodifiableList(vouchers);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException("[ERROR] 바우처가 존재하지 않습니다.");
        }

        return Optional.of(storage.get(id));
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
