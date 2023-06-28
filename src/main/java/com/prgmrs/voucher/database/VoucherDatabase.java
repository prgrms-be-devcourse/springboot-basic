package com.prgmrs.voucher.database;

import com.prgmrs.voucher.model.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherDatabase {
        Map<UUID, Voucher> load();
        void store(UUID voucherId, Voucher voucher);
}
