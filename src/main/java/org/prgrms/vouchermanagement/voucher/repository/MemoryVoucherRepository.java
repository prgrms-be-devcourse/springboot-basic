package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("memory")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return storage.values()
                .stream()
                .filter(voucher -> voucher.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVoucherByCustomerId(UUID customerId) {
        storage.values()
                .stream()
                .filter(voucher -> voucher.getCustomerId().equals(customerId))
                .forEach(voucher -> storage.remove(voucher.getVoucherId()));
    }

}
