package org.prgrms.kdtspringdemo.domain.voucher.storage;

import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class VoucherMemoryStorage implements VoucherStorage {
    // ConcurrentHashMap 을 통한 메모리 저장소
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public Map<UUID, Voucher> getStorage() {
        return storage;
    }

}
