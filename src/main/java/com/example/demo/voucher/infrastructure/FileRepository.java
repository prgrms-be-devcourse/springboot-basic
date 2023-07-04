package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileRepository implements VoucherRepository {

    private final VoucherInfo vouchers;

    @Value("${file.path}")
    private String filePath;

    public FileRepository(VoucherInfo vouchers) {
        this.vouchers = vouchers;
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
        try {
            write(voucher);
            vouchers.add(voucher);
        } catch (IOException e) {
            throw new RuntimeException("File writing failed", e);
        }
    }

    private void write(Voucher voucher) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(voucher.getName());
            writer.write(",");
            writer.write(voucher.getVoucherId().toString());
            writer.write(",");
            writer.write(Long.toString(voucher.getValue()));
            writer.newLine();
        }
    }
}
