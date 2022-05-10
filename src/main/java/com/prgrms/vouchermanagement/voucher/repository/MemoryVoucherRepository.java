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

    private final Map<Long, Voucher> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public Long save(Voucher voucher) {
        if (voucher == null) {
            return -1L;
        }
        Long voucherId = ++sequence;
        store.put(voucherId, voucher);
        return voucherId;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
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
    public void remove(Long voucherId) {
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

    @Override
    public List<Voucher> findVoucherByCustomer(Long customerId) {
        return null;
    }
}
