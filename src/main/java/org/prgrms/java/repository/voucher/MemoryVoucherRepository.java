package org.prgrms.java.repository.voucher;

import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Collection<Voucher> findAll() {
        return storage.values();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new VoucherException(String.format("Already exists voucher having id %s", voucher.getVoucherId()));
        }
        storage.put(voucher.getVoucherId(), voucher);
        return storage.get(voucher.getVoucherId());
    }

    @Override
    public Voucher update(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isEmpty()) {
            throw new VoucherException(String.format("No exists voucher having id %s", voucher.getVoucherId()));
        }
        storage.put(voucher.getVoucherId(), voucher);
        return storage.get(voucher.getVoucherId());
    }

    @Override
    public long deleteAll() {
        long count = storage.size();
        storage.clear();

        return count;
    }
}
