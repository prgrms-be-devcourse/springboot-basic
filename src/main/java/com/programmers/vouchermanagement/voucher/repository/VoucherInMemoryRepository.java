package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.programmers.vouchermanagement.constant.Message.NOT_DELETED;
import static com.programmers.vouchermanagement.constant.Message.NOT_UPDATED;

@Repository
@Profile("memory")
public class VoucherInMemoryRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers;

    public VoucherInMemoryRepository() {
        vouchers = new HashMap<>();
    }

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.voucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public void delete(UUID id) {
        Optional.ofNullable(vouchers.remove(id)).orElseThrow(() -> new RuntimeException(NOT_DELETED));
    }

    @Override
    public void deleteAll() {
        if (!vouchers.isEmpty())
            vouchers.clear();
    }

    @Override
    public void update(Voucher voucher) {
        Optional.ofNullable(vouchers.get(voucher.voucherId())).orElseThrow(() -> new RuntimeException(NOT_UPDATED));
        vouchers.put(voucher.voucherId(), voucher);
    }
}
