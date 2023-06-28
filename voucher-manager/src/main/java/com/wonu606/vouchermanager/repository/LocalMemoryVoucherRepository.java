package com.wonu606.vouchermanager.repository;

import com.wonu606.vouchermanager.domain.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class LocalMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherStore;

    public LocalMemoryVoucherRepository() {
        voucherStore = new ConcurrentHashMap<>();
    }

    public LocalMemoryVoucherRepository(Map<UUID, Voucher> voucherStore) {
        this.voucherStore = voucherStore;
    }

    @Override
    public void save(Voucher voucher) {
        persistVoucher(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID uuid) {
        return Optional.ofNullable(voucherStore.get(uuid));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherStore.values());
    }

    @Override
    public void deleteById(UUID uuid) {
        voucherStore.remove(uuid);
    }

    @Override
    public void deleteAll() {
        voucherStore.clear();
    }

    private void persistVoucher(Voucher voucher) {
        voucherStore.put(voucher.getUuid(), voucher);
    }
}
