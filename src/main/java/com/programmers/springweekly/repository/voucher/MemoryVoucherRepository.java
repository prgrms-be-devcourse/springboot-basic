package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> getList() {
        return Collections.unmodifiableMap(voucherMap);
    }
}
