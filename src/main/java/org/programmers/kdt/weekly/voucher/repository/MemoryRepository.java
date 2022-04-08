package org.programmers.kdt.weekly.voucher.repository;

import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class MemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap();

    @Override
    public void insert(UUID voucherId, Voucher voucher) {
        storage.put(UUID.randomUUID(), voucher);
    }

    @Override
    public int getSize() {
        return storage.size();
    }


    @Override
    public void showAll() {
        storage.forEach((uuid, voucher) -> System.out.println(voucher.toString()));
    }
}
