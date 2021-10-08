package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(final UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher updateEmail(final Voucher voucher, final String email) {
        // JDBC만 구현
        return voucher;
    }

    @Override
    public Optional<List<Voucher>> findByEmail(final String email) {
        // JDBC만 구현
        return Optional.empty();
    }

    @Override
    public void delete(final UUID voucherId) {
        // JDBC만 구현
    }

    @Override
    public List<Customer> findCustomer(final UUID voucherId) {
        // JDBC만 구현
        return null;
    }

    @Override
    public Voucher insert(final Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
}
