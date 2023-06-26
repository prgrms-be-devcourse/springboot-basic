package me.kimihiqq.vouchermanagement.repository;

import me.kimihiqq.vouchermanagement.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(UUID voucherId) {
        store.remove(voucherId);
    }
}
