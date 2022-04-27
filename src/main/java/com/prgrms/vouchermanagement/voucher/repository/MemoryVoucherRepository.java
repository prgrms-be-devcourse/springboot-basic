package com.prgrms.vouchermanagement.voucher.repository;

import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> store = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        if (voucher == null) {
            return;
        }
        store.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public void update(Voucher voucher) {
        if (voucher == null) {
            return;
        }

        if (store.containsKey(voucher.getVoucherId())) {
            store.put(voucher.getVoucherId(), voucher);
        }
    }

    @Override
    public void remove(UUID voucherId) {
        if (voucherId == null) {
            return;
        }
        store.remove(voucherId);
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findByPeriod(LocalDateTime from, LocalDateTime end) {
        return null;
    }
}
