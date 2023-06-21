package org.prgms.vouchermanagement.repository;

import org.prgms.vouchermanagement.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FixedAmountVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> fixedVoucherStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(fixedVoucherStorage.get(voucherId));
    }

    @Override
    public void saveVoucher(UUID voucherId, Voucher voucher) {
        fixedVoucherStorage.put(voucherId, voucher);
    }

    @Override
    public Optional<Map<UUID, Voucher>> getVoucherList() {
        if (!fixedVoucherStorage.isEmpty()) {
            return Optional.of(Collections.unmodifiableMap(fixedVoucherStorage));
        }
        return Optional.empty();
    }
}
