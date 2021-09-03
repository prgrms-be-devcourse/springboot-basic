package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("test")
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> repository = new ConcurrentHashMap<UUID, Voucher>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return repository.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        repository.remove(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Voucher addOwner(Customer customer, Voucher voucher) {
        // TODO: Implement
        return null;
    }

    @Override
    public void removeOwner(Customer customer, UUID voucherId) {
        // TODO: Implement
    }

    @Override
    public Optional<UUID> findCustomerIdByVoucherId(UUID voucherId) {
        // TODO: Implement
        return Optional.empty();
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        // TODO: Implement
        return null;
    }
}
