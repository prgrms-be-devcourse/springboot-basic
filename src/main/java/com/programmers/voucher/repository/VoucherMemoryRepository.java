package com.programmers.voucher.repository;

import com.programmers.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("voucher save at memory => {}", voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("voucher findAll at memory => {}", storage.values());
        return new ArrayList<>(storage.values());
    }
}
