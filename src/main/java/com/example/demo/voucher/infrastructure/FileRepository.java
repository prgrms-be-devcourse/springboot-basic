package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileRepository implements VoucherRepository {

    private final VoucherInfo vouchers;
    private final VoucherFileWriter writer;

    @Value("${file.path}")
    private String filePath;

    public FileRepository(VoucherInfo vouchers, VoucherFileWriter writer) {
        this.vouchers = vouchers;
        this.writer = writer;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return vouchers.getVoucherList()
                .stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .limit(1)
                .findAny();
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.getVoucherList();
    }

    @Override
    public void insert(Voucher voucher) {
        writer.write(voucher, filePath);
        vouchers.add(voucher);
    }

    @Override
    public void deleteAll() {
        writer.delete(filePath);
        vouchers.clear();
    }
}