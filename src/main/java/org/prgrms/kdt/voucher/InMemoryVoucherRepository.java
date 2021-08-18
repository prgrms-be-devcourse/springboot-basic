package org.prgrms.kdt.voucher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 1:31 오전
 */
public class InMemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(vouchers.get(voucherId));
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return Collections.unmodifiableMap(vouchers);
    }
}
