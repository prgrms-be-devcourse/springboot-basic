package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        Voucher voucher = storage.get(id);
        if (voucher == null) {
            return Optional.empty();
        }

        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public void update(UUID id, VoucherType type, long amount) {
        storage.putIfAbsent(id, type.createVoucher(id, amount));
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
