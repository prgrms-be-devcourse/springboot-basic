package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream()
                .toList();
    }

    @Override
    public void updateVoucher(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public Optional<Voucher> findVoucherById(UUID voucher_id) {
        return Optional.ofNullable(storage.get(voucher_id));
    }
}
