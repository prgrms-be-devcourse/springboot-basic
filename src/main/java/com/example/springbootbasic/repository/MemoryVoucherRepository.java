package com.example.springbootbasic.repository;

import com.example.springbootbasic.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private static final Map<Long, Voucher> storage = new HashMap<>();

    @Override
     public synchronized Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
//        logger.debug("[SAVE] - voucherId => '{}', voucher => '{}'", sequence, voucher.getVoucherEnum());
        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new ArrayList<>(storage.values());
    }
}