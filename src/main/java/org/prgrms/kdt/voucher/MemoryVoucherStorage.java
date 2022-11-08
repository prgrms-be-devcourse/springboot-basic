package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collector;

@Repository
public class MemoryVoucherStorage implements VoucherStorage {

    private final Map<UUID, Voucher> memoryVoucherStorage = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        memoryVoucherStorage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return memoryVoucherStorage.values()
                .stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(memoryVoucherStorage.get(voucherId));
    }
}
