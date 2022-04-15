package org.programmers.kdt.weekly.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("dev")
@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap();

    @Override
    public void insert(Voucher voucher) {
        storage.put(UUID.randomUUID(), voucher);
    }

    @Override
    public int count() {
        return storage.size();
    }


    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        storage.forEach((uuid, voucher) -> voucherList.add(voucher));
        return voucherList;
    }
}
