package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("prod")
public class InMemoryVoucherRepository implements VoucherRepository {

    private AtomicLong sequencer = new AtomicLong(0);
    private Map<Long, Voucher> db = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(InMemoryVoucherRepository.class);

    @Override
    public Voucher save(Voucher voucher) {
        long id = sequencer.getAndAdd(1);
        voucher.setId(id);
        db.put(id, voucher);
        log.debug("Saved voucher '{}' to database.", voucher);
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public void loadVouchers() {
        throw new UnsupportedOperationException("Loading vouchers does not supported by in-memory db.");
    }

    @Override
    public void persistVouchers() {
        throw new UnsupportedOperationException("Persisting vouchers does not supported by in-memory db.");
    }

    @Override
    public Optional<Voucher> findById(long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Voucher> findAllByCustomer(long customerId) {
        return db.values().stream().filter(voucher -> voucher.getCustomerId() == customerId).collect(Collectors.toList());
    }

    @Override
    public void update(Voucher voucher) {
        final Voucher updatingVoucher = db.get(voucher.getId());
        updatingVoucher.setName(voucher.getName());
        updatingVoucher.setDiscountPolicy(voucher.getDiscountPolicy());
        updatingVoucher.setCustomerId(voucher.getCustomerId());
    }

    @Override
    public void deleteById(long id) {
        db.remove(id);
    }
}
