package com.example.springbootbasic.repository;

import com.example.springbootbasic.domain.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherRepository.class);
    private static final Map<Long, Voucher> storage = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Long save(Long voucherId, Voucher voucher) {
        storage.put(voucherId, voucher);
        logger.debug("[SAVE] - voucherId => '{}', voucher => '{}'",
                voucherId,
                voucher.getVoucherEnum());
        return voucherId;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new ArrayList<>(
                storage.values()
                        .stream()
                        .toList());
    }

    @Override
    public Long getSequence() {
        return sequence++;
    }
}