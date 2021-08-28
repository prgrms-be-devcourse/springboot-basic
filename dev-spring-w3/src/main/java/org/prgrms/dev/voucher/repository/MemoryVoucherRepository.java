package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> STORE = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(STORE.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(STORE.values());
    }

    @Override
    public Voucher create(Voucher voucher) {
        STORE.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

}
