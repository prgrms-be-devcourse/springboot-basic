package org.prgrms.kdt.domain;

import org.prgrms.kdt.strategy.Voucher;

import java.util.*;

public class Vouchers implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap;

    public Vouchers() {
        this.voucherMap = new HashMap<>();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
