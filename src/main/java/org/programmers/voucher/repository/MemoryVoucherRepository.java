package org.programmers.voucher.repository;

import org.programmers.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final List<Voucher> database = new ArrayList<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        logger.info("findById function");
        return database.stream()
                .filter(item -> item.getVoucherId().equals(voucherId))
                .findAny();
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("findAll function");
        return database;
    }

    @Override
    public void save(Voucher voucher) {
        logger.info("database add start");
        database.add(voucher);
        logger.info("database add success");
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
