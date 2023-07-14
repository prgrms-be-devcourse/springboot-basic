package org.promgrammers.voucher.repository;

import org.promgrammers.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    //동시성 문제
    private final Map<UUID, Voucher> map = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Voucher save(Voucher voucher) {
        map.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteAll() {
        map.clear();
    }
}
