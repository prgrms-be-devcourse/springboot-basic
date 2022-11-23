package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.voucher.domain.Voucher;

import java.util.*;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("local")
@Repository
public class InMemoryVoucherStorage implements VoucherStorage {

    private final Map<UUID, Voucher> vouchers = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getUUID(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public Optional<Voucher> findById(UUID uuid) {
        return Optional.ofNullable(vouchers.get(uuid));
    }
}
