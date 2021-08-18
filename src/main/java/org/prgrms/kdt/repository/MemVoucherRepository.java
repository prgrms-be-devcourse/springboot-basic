package org.prgrms.kdt.repository;

import org.prgrms.kdt.entity.Voucher;

import java.util.*;

public class MemVoucherRepository implements VoucherRepository {
    private static final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        if (vouchers.containsKey(voucherId)) {
            return Optional.of(vouchers.get(voucherId));
        }
        return Optional.empty();
    }

    @Override
    public Collection<Voucher> findAllVoucher() {
        return vouchers.values();
    }

    @Override
    public void insert(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }

}
