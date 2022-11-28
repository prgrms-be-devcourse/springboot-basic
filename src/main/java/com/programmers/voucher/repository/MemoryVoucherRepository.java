package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Qualifier("Memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final List<Voucher> storage = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    @Override
    public void insert(Voucher voucher) {
        storage.add(voucher);
        logger.info("voucher 가 저장되었습니다.");
    }

    @Override
    public List<Voucher> findAll() {
        return storage;
    }

    @Override
    public List<Voucher> findByCustomerEmail(String customerEmail) {
        return null;
    }

    @Override
    public List<Voucher> findByDate(String customerEmail, LocalDateTime date) {
        return null;
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void update(UUID voucherId, long discount) {

    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }
}
