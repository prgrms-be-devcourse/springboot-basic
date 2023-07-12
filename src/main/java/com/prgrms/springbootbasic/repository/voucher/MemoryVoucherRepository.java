package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher create(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        return Optional.ofNullable(voucher);
    }

    @Override
    public Optional<Voucher> findByCreatedAt(LocalDateTime createAt) {
        Optional<Voucher> findVoucher = storage.values().stream()
                .filter(voucher -> voucher.getCreatedAt().equals(createAt))
                .findFirst();
        return findVoucher;
    }

    @Override
    public List<Voucher> findAll(Voucher voucher) {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        UUID voucherId = voucher.getVoucherId();
        if (storage.containsKey(voucherId)) {
            storage.put(voucherId, voucher);
            return Optional.of(voucher);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> deleteById(UUID voucherId) {
        Voucher removeVoucher = storage.remove(voucherId);
        return Optional.ofNullable(removeVoucher);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }
}
