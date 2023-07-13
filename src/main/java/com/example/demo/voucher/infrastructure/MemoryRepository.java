package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class MemoryRepository implements VoucherRepository {
    private final List<Voucher> vouchers = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return vouchers.stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers);
    }

    @Override
    public void insert(Voucher voucher) {
        vouchers.add(voucher);
    }

}