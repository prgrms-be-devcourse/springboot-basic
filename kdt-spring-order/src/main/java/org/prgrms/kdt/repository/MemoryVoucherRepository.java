package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.*;

public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> voucherMap = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.get(voucherId));
    }

    @Override
    public List<Voucher> findVouchers() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }
}
