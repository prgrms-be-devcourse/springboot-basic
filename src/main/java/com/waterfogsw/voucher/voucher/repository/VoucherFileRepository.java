package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoucherFileRepository implements VoucherRepository {
    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.empty();
    }


    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
