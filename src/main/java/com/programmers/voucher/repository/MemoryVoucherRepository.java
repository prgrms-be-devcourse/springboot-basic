package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemoryVoucherRepository {

    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);


    public Optional<Voucher> findByID(UUID voucherID) {
        return Optional.ofNullable(storage.get(voucherID));
    }

    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("voucher 가 저장되었습니다.");
        return voucher;
    }


    public Map<UUID, Voucher> findAll() {
        return storage;
    }


}
