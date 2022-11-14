package org.prgrms.kdt.storage;

import org.prgrms.kdt.storage.VoucherStorage;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherStorage implements VoucherStorage {

    private final Map<UUID, Voucher> memoryVoucherStorage = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MemoryVoucherStorage.class);

    @Override
    public void save(Voucher voucher) {
        memoryVoucherStorage.put(voucher.getVoucherId(), voucher);
        logger.info("바우처가 성공적으로 저장되었습니다.");
    }

    @Override
    public List<Voucher> findAll() {
        return memoryVoucherStorage.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(memoryVoucherStorage.get(voucherId));
    }
}
