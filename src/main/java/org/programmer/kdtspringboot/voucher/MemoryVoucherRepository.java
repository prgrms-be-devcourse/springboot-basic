package org.programmer.kdtspringboot.voucher;

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
@Profile("memory")
public class MemoryVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);

    @Override
    public void saveVoucher(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("voucher 생성(" + voucher.getVoucherId() + "," + voucher.getValue() +","+ voucher.getClass()+")");
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }
}
