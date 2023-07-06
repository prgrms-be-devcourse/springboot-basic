package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getId(), voucher);

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Voucher> findVoucherById(String id) {
        return Optional.empty();
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }
}
