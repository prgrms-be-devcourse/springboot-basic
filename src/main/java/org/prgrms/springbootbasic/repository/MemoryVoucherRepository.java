package org.prgrms.springbootbasic.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.springbootbasic.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"local", "default"})
public class MemoryVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Voucher voucher) {

        logger.info("MemoryVoucherRepository.save() called");

        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {

        logger.info("MemoryVoucherRepository.findAll() called");

        return storage.values()
            .stream()
            .toList();
    }

    @Override
    public Integer getVoucherTotalNumber() {
        return storage.size();
    }

    @Override
    public void removeAll() {
        storage.clear();
    }
}
