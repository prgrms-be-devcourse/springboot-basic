package org.prgms.management.voucher.repository;

import org.prgms.management.customer.entity.Customer;
import org.prgms.management.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local-memory")
public class VoucherMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        if (!voucherMap.containsKey(voucher.getVoucherId())) {
            voucherMap.put(voucher.getVoucherId(), voucher);
            return Optional.of(voucher);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        return Optional.ofNullable(
                voucherMap.replace(voucher.getVoucherId(), voucher));
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        return voucherMap;
    }

    @Override
    public Optional<Voucher> getById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.get(voucherId));
    }

    @Override
    public Optional<Voucher> getByCreatedAt(LocalDateTime cratedAt) {
        return voucherMap.values().stream()
                .filter(voucher -> voucher.getCreatedAt() == cratedAt)
                .findFirst();
    }

    @Override
    public Optional<Voucher> getByType(String type) {
        return voucherMap.values().stream()
                .filter(voucher -> Objects.equals(voucher.getVoucherType(), type))
                .findFirst();
    }

    @Override
    public Optional<Voucher> delete(UUID voucherId) {
        return voucherMap.values().stream()
                .filter(voucher -> voucher.getVoucherId() == voucherId)
                .findFirst();
    }

    @Override
    public void deleteAll() {
        voucherMap.clear();
    }
}
