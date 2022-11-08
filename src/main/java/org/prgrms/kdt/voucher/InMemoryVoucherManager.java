package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryVoucherManager implements VoucherManager {

    private final Map<AtomicLong, Voucher> vouchers = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(sequence, voucher);
        sequence.incrementAndGet();
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(vouchers.values());
    }

}