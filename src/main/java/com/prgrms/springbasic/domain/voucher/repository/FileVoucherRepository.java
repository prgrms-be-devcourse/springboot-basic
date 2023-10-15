package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.util.CsvFileUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private static Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
    private static final String FILE_PATH = "voucher.csv";

    public FileVoucherRepository() {
        vouchers = CsvFileUtil.loadVoucherFromFile(FILE_PATH);
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        CsvFileUtil.addVoucherToFile(FILE_PATH, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream()
                .toList();
    }
}
