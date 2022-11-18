package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"dev", "default"})
public class MemoryVoucherStorage implements VoucherStorage {

    private final Map<String, Voucher> memoryVoucherStorage = new HashMap<>();

    @Override
    public void save(Voucher voucher) {
        memoryVoucherStorage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return memoryVoucherStorage.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        return Optional.ofNullable(memoryVoucherStorage.get(voucherId));
    }

    @Override
    public void deleteById(String voucherId) {

    }
}
