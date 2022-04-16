package org.programmers.voucher.repository;

import org.programmers.voucher.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private final List<Voucher> database = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return database.stream()
                .filter(item -> item.getVoucherId().equals(voucherId))
                .findAny();
    }

    @Override
    public List<Voucher> findAll() {
        return database;
    }

    @Override
    public void save(Voucher voucher) {
        database.add(voucher);
    }
}
