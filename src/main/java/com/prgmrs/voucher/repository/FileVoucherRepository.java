package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.database.VoucherDatabase;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileVoucherRepository implements VoucherRepository {
    private static final String FILE_PATH = "csv/vouchers.csv";
    private final VoucherDatabase voucherDatabase;

    public FileVoucherRepository(VoucherDatabase voucherDatabase) {
        this.voucherDatabase = voucherDatabase;
    }

    @Override
    public void save(Voucher voucher) {
        voucherDatabase.store(voucher, FILE_PATH);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherDatabase.load(FILE_PATH);
    }

    @Override
    public List<Voucher> findByUsername() {
        return null;
    }
}
