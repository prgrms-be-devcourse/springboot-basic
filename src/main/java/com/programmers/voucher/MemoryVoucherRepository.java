package com.programmers.voucher;

import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public Optional<Voucher> findByID(UUID voucherID) {
        return Optional.ofNullable(storage.get(voucherID));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherID(), voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return storage;
    }
}
