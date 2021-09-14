package org.prgrms.kdt.engine.voucher.repository;

import org.prgrms.kdt.engine.voucher.VoucherType;
import org.prgrms.kdt.engine.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Optional<Map<UUID, Voucher>> getAll() {
        if (storage.isEmpty())
            return Optional.empty();
        return Optional.of(storage);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void setCustomerId(UUID voucherId, UUID customerId) {
        // 미구현
    }

    @Override
    public Optional<Map<UUID, Voucher>> getCustomerVoucher(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public void deleteCustomerVoucher(UUID customerId) {

    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        // 미구현
    }

    @Override
    public Optional<UUID> findCustomerByVoucher(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        // 미구현
        return Collections.emptyList();
    }

    public void deleteAll() { storage.clear(); };
}
