package org.prgrms.kdt.voucher.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.kdt.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

//@Repository
//@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        return storage.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Voucher> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void update(UUID id, long value) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(UUID id) {

    }

}
