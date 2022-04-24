package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("voucher 생성 "+ voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(storage.get(voucherId));
        return voucher;
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public void deleteById(UUID voucherId) {
        storage.remove(voucherId);
    }
}
