package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(VoucherMemoryRepository.class);

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.debug("바우처 생성 -> " + voucher.toString());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.debug("모든 바우처의 갯수 : " + storage.size());
        return new ArrayList<>(storage.values());
    }
}
