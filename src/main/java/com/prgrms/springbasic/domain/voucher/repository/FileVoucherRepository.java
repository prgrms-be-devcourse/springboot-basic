package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.util.CsvFileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();

    @Value("${repository.voucher.filePath}")
    private String filePath;

    @PostConstruct
    public void init() {
        vouchers = CsvFileUtil.readVoucherFromFile(filePath);
    }

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        CsvFileUtil.addObjectToFile(filePath, voucher.toString());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream()
                .toList();
    }
}
