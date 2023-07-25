package org.weekly.weekly.voucher.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.weekly.weekly.global.handler.ExceptionCode;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storages = new ConcurrentHashMap<>();

    public Voucher insert(Voucher voucher) {
        validateUUID(voucher.getVoucherId());
        storages.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storages.get(voucherId));
    }

    public List<Voucher> findAll() {
        return storages.values().stream()
                .toList();
    }

    @Override
    public List<Voucher> findByDiscountType(DiscountType discountType) {
        return null;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }

    private void validateUUID(UUID uuid) {
        Optional<Voucher> voucherOptional = findById(uuid);
        if (voucherOptional.isPresent()) {
            throw new VoucherException(ExceptionCode.VOUCHER_EXIST);
        }
    }
}
