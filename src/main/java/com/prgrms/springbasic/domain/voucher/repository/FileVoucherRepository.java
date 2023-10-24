package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.util.CsvFileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
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
        String fieldToCsvString = String.format("%s,%s,%s", voucher.getVoucherId(), voucher.getDiscountType(), voucher.getDiscountValue());
        CsvFileUtil.addObjectToFile(filePath, fieldToCsvString);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream()
                .toList();
    }

    @Override
    public void updateVoucher(Voucher voucher) {
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<Voucher> findVoucherById(UUID voucher_id) {
        return Optional.empty();
    }
}
