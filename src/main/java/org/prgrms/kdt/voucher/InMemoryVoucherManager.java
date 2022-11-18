package org.prgrms.kdt.voucher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class InMemoryVoucherManager implements VoucherManager {

    private final Map<Long, Voucher> vouchers = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .collect(getUnmodifiableListCollector());
    }

    @Override
    public Optional<Voucher> findById(long id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }

    @Override
    public void update(Voucher voucher) {
        throw new UnsupportedOperationException("Unsupported command.");
    }

    @Override
    public void deleteById(long voucherId) {
        throw new UnsupportedOperationException("Unsupported command.");
    }

    private static Collector<Voucher, Object, List<Voucher>> getUnmodifiableListCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                Collections::unmodifiableList
        );
    }

}