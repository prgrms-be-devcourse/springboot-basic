package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
public class InMemoryVoucherManager implements VoucherManager {

    private final Map<Long, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .collect(getUnmodifiableListCollector());
    }

    private static Collector<Voucher, Object, List<Voucher>> getUnmodifiableListCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                Collections::unmodifiableList
        );
    }

}