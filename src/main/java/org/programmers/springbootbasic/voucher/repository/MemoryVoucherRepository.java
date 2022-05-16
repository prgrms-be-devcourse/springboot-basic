package org.programmers.springbootbasic.voucher.repository;

import org.programmers.springbootbasic.voucher.model.Voucher;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev"})
public class MemoryVoucherRepository implements VoucherRepository {

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

    @Override
    public List<Voucher> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.remove(voucher.getVoucherId());
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findByCreatedAt() {
        return null;
    }

    @Override
    public int getCountByVoucherId(UUID voucherId) {
        return 0;
    }
}
