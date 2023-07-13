package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return storage.get(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public Voucher update(Voucher voucher) {
        if (findById(voucher.getVoucherId()) == null) {
            return null;
        }

        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Voucher deleteById(UUID voucherId) {
        return storage.remove(voucherId);
    }
}
