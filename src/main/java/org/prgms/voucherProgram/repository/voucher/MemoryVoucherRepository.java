package org.prgms.voucherProgram.repository.voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("Voucher save at memory => {}", voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Voucher read at memory => {}", storage.values());
        return new ArrayList<>(storage.values());
    }
}
