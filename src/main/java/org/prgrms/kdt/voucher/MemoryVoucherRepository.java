package org.prgrms.kdt.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new HashMap<>(); // voucher를 담을 메모리

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(voucherMap.get(voucherId));
    }

    @Override
    public boolean save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        return true;
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return new ArrayList<>(voucherMap.values());
    }
}
