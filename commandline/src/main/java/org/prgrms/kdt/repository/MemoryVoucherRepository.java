package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("findAll()->List<Voucher> 리턴");
        return new ArrayList<>(storage.values());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        logger.info("생성된 Voucher insert");
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

}
