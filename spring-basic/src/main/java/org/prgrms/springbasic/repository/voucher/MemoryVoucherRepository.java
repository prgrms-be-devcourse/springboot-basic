package org.prgrms.springbasic.repository.voucher;

import org.prgrms.springbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public int countStorageSize() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }
}
