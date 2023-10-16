package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.util.CsvFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {
    private static Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
    private final String filePath;

    public FileVoucherRepository(@Value("${repository.voucher.filePath}") String filePath) {
        this.filePath = filePath;
        vouchers = CsvFileUtil.loadVoucherFromFile(filePath);
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        CsvFileUtil.addVoucherToFile(filePath, voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream()
                .toList();
    }
}
