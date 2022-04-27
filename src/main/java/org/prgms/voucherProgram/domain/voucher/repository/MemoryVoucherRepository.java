package org.prgms.voucherProgram.domain.voucher.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.prgms.voucherProgram.domain.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        if (storage.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher update(Voucher voucher) {
        throw new AssertionError();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findByTypeAndDate(int type, LocalDateTime start, LocalDateTime end) {
        throw new AssertionError();
    }

    @Override
    public void deleteById(UUID voucherId) {
        throw new AssertionError();
    }

    @Override
    public void deleteAll() {
        throw new AssertionError();
    }

    @Override
    public Voucher assignCustomer(Voucher voucher) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findByCustomerEmail(String customerEmail) {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findNotAssign() {
        throw new AssertionError();
    }

    @Override
    public List<Voucher> findAssigned() {
        throw new AssertionError();
    }
}
