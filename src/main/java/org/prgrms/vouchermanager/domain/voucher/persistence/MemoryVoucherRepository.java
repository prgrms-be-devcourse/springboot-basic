package org.prgrms.vouchermanager.domain.voucher.persistence;

import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;
import org.prgrms.vouchermanager.domain.voucher.domain.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (storage.get(voucher.getId()) != null)
            throw new IllegalArgumentException("이미 존재하는 voucherId 입니다.");

        storage.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getAll() {
        return new ArrayList<>(storage.values());
    }
}
