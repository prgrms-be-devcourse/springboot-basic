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
    private static final Map<UUID, Voucher> storage = new HashMap<>();

    @Override
    public Map<UUID, Voucher> load(String filePath) {
        return storage;
    }

    @Override
    public void store(Voucher voucher, String filePath) {
        storage.put(voucher.getVoucherId(), voucher);
    }
}
