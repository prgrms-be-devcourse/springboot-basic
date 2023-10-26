package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public int create(UUID voucherId, DiscountPolicy discountPolicy) {
        Voucher voucher = new Voucher(voucherId, discountPolicy);
        storage.put(voucherId, voucher);

        return 0;
    }

    @Override
    public List<Voucher> voucherLists() {
        return storage.values().stream()
                .toList();
    }

    @Override
    public void update(UUID voucherId, long amount) {
    }

    @Override
    public int deleteAll() {

        return 0;
    }

    @Override
    public Voucher getById(UUID voucherId) {
        return storage.get(voucherId);
    }
}
