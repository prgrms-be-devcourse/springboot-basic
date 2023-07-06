package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> getAllVouchersList() {
        return Collections.unmodifiableMap(storage);
    }
}
