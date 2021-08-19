package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.get(voucherId));
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
