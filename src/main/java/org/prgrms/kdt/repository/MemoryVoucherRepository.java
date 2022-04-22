package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map getVoucherList() {
        return storage;
    }

    @Override
    public Voucher delete(Voucher voucher) {
        return null;
    }

    @Override
    public Optional<Voucher> getByVoucherId(UUID voucherId) {
        return Optional.empty();
    }
}
