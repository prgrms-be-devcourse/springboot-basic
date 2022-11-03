package org.prgrms.kdt.repository;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public void insert(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getAllStoredVoucher() {
        List<Voucher> storedVoucher = new ArrayList<>();
        for (UUID voucherId :repository.keySet()) {
            storedVoucher.add(repository.get(voucherId));
        }

        return storedVoucher;
    }

    @Override
    public void clear() {
        repository.clear();
    }
}
