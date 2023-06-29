package com.prgmrs.voucher.database;

import com.prgmrs.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("dev")
public class InMemoryVoucherDatabase implements VoucherDatabase {
    private static Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Map<UUID, Voucher> load() {
        return storage;
    }

    @Override
    public void store(UUID voucherId, Voucher voucher) {
        storage.put(voucherId, voucher);
    }
}
