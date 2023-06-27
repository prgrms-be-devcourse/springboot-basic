package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.database.InMemoryVoucherDatabase;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class VoucherRepositoryImpl implements VoucherRepository {

    private InMemoryVoucherDatabase inMemoryVoucherDatabase;

    @Autowired
    public VoucherRepositoryImpl(InMemoryVoucherDatabase inMemoryVoucherDatabase) {
        this.inMemoryVoucherDatabase = inMemoryVoucherDatabase;
    }

    @Override
    public void save(Voucher voucher) {
        inMemoryVoucherDatabase.putCache(voucher.getVoucherId(), voucher);
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return inMemoryVoucherDatabase.getCache();
    }

    @Override
    public Voucher findVoucherById(UUID uuid) {
        Map<UUID, Voucher> cache = inMemoryVoucherDatabase.getCache();
        Voucher voucher = cache.get(uuid);
        return voucher;
    }
}
