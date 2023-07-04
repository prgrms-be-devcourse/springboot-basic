package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.database.VoucherDatabase;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {
    private static final String FILEPATH = "csv/vouchers.csv";
    private final VoucherDatabase voucherDatabase;

    public VoucherRepositoryImpl(VoucherDatabase voucherDatabase) {
        this.voucherDatabase = voucherDatabase;
    }

    @Override
    public void save(Voucher voucher) {
        voucherDatabase.store(voucher.getVoucherId(), voucher, FILEPATH);
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return voucherDatabase.load(FILEPATH);
    }

    @Override
    public Voucher findVoucherById(UUID uuid) {
        Map<UUID, Voucher> storage = voucherDatabase.load(FILEPATH);
        return storage.get(uuid);
    }
}
