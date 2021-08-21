package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository {

    private static Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Voucher create(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

}
