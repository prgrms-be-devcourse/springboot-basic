package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.database.VoucherDatabase;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private VoucherDatabase voucherDatabase;

    @Autowired
    public VoucherRepositoryImpl(VoucherDatabase voucherDatabase) {
        this.voucherDatabase = voucherDatabase;
    }

    @Override
    public void save(Voucher voucher) {
        voucherDatabase.putCache(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return voucherDatabase.getCache();
    }

    @Override
    public Voucher findVoucherById(UUID uuid) {
        Map<UUID, Voucher> cache = voucherDatabase.getCache();
        return cache.get(uuid);
    }
}
