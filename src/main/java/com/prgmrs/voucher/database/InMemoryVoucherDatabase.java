package com.prgmrs.voucher.database;

import com.prgmrs.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class InMemoryVoucherDatabase implements VoucherDatabase {
    private static final List<Voucher> storage = new ArrayList<>();

    @Override
    public List<Voucher> load(String filePath) {
        return storage;
    }

    @Override
    public void store(Voucher voucher, String filePath) {
        storage.add(voucher);
    }
}
