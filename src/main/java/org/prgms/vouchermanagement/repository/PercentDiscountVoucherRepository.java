package org.prgms.vouchermanagement.repository;

import org.prgms.vouchermanagement.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PercentDiscountVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> percentVoucherStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(percentVoucherStorage.get(voucherId));
    }

    @Override
    public void saveVoucher(UUID voucherId, Voucher voucher) {
        percentVoucherStorage.put(voucherId, voucher);
    }
}
