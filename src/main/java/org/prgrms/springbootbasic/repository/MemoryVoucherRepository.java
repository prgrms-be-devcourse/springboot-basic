package org.prgrms.springbootbasic.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Voucher;

public class MemoryVoucherRepository {

    private final Map<UUID, Voucher> store = new HashMap<>();

    public void save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
    }

    public Optional<List<Voucher>> findAll() {
        return Optional.ofNullable(store.values()
            .stream()
            .toList());
    }

    public Integer getVoucherTotalNumber() {
        return store.size();
    }

    public void removeAll() {
        store.clear();
    }
}
