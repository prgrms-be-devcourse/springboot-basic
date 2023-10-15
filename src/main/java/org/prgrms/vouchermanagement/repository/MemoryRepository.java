package org.prgrms.vouchermanagement.repository;

import org.prgrms.vouchermanagement.voucher.Voucher;
import org.springframework.context.annotation.Primary;
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
    public void create(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Voucher getById(UUID voucherId) {
        return storage.get(voucherId);
    }

    @Override
    public List<Voucher> voucherLists() {
        System.out.println("local");
        return storage.values().stream()
                .toList();
    }
}
