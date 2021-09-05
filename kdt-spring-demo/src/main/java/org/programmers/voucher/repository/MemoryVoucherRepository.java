package org.programmers.voucher.repository;

import org.programmers.voucher.model.VoucherInterface;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements FileMemoryVoucherRepository {
    private final Map<UUID, VoucherInterface> voucherMemoryMap = new ConcurrentHashMap<>();

    @Override
    public Optional<VoucherInterface> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMemoryMap.get(voucherId));
    }

    @Override
    public List<VoucherInterface> findAllVouchers() {
        return new ArrayList<>(voucherMemoryMap.values());
    }

    @Override
    public void save(VoucherInterface voucher) {
        voucherMemoryMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteAll() {
        voucherMemoryMap.clear();
    }
}
