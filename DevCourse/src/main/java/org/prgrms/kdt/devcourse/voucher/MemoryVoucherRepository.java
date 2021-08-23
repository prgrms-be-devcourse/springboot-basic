package org.prgrms.kdt.devcourse.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private Map<UUID,Voucher> voucherList = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherList.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherList.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return voucherList.values().stream().toList();
    }
}
