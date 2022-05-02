package org.prgms.kdtspringvoucher.voucher.repository;

import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository{

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        logger.info("Succeed save Voucher Data => {}", voucher);
        return store.put(voucher.getVoucherId(),voucher);
    }

    @Override
    public Voucher update(Voucher voucher) {
        logger.info("Succeed update Voucher Data => {}", voucher);
        return store.put(voucher.getVoucherId(),voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> findVoucher = Optional.ofNullable(store.get(voucherId));
        logger.info("Search by voucherId => {}", voucherId);
        logger.info("Find Voucher by voucherId => {}", findVoucher);
        return findVoucher;
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findByParam(VoucherType voucherType, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Find All Saved vouchers");
        return store.values().stream().toList();
    }

    @Override
    public void deleteAll() {
        store.clear();
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }

    @Override
    public void deleteById(UUID voucherId) {

    }
}
