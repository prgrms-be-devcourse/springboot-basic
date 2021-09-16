package org.prgrms.devcourse.repository;

import org.prgrms.devcourse.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }
}
