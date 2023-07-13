package org.prgms.vouchermanagement.voucher.domain.repository;

import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherStorage.get(voucherId));
    }

    @Override
    public Optional<Voucher> saveVoucher(Voucher voucher) {
        voucherStorage.put(voucher.getVoucherId(), voucher);
        return Optional.of(voucher);
    }

    @Override
    public Map<UUID, Voucher> getVoucherList() {
        if (!voucherStorage.isEmpty()) {
            return Collections.unmodifiableMap(voucherStorage);
        }
        return Collections.emptyMap();
    }
}
