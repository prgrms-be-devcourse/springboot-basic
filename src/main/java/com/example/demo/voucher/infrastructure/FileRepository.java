package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileRepository implements VoucherRepository {
    private final VoucherSerializer serializer;

    public FileRepository(VoucherSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return serializer.deserialize().stream()
                    .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                    .findFirst();
        } catch (IOException e) {
            throw new RuntimeException("File loading failed", e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return serializer.deserialize();
        } catch (IOException e) {
            throw new RuntimeException("File loading failed", e);
        }
    }

    @Override
    public void insert(Voucher voucher) {
        try {
            List<Voucher> vouchers = serializer.deserialize();
            vouchers.add(voucher);
            serializer.serialize(vouchers);
        } catch (IOException e) {
            throw new RuntimeException("File writing failed", e);
        }
    }

    @Override
    public void deleteAll() {

    }
}
