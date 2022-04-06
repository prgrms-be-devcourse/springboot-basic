package org.prgrms.springbootbasic.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.springbootbasic.entity.Voucher;

public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<List<Voucher>> findAll() {
        return Optional.ofNullable(storage.values()
            .stream()
            .toList());
    }

    @Override
    public Integer getVoucherTotalNumber() {
        return storage.size();
    }

    @Override
    public void removeAll() {
        storage.clear();
    }
}
