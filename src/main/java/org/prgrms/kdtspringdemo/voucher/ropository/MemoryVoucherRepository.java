package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher = storage.get(voucherId);
        if (voucher == null) {
            return Optional.empty();
        }

        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public Optional<Voucher> update(UUID voucherId, VoucherType voucherType, long amount) {
        Voucher updatedVoucher = storage.putIfAbsent(voucherId, voucherType.updateVoucher(voucherId, amount));

        return updatedVoucher == null ? Optional.empty() : Optional.of(updatedVoucher);
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }
}
