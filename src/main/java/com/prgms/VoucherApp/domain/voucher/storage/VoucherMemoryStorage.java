package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("default")
public class VoucherMemoryStorage implements VoucherStorage {

    private Map<UUID, Voucher> voucherLinkedMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        Voucher voucher = voucherLinkedMap.get(voucherId);
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherLinkedMap
                .values()
                .stream()
                .toList();
    }

    @Override
    public void save(Voucher voucher) {
        voucherLinkedMap.put(voucher.getUUID(), voucher);
    }
}
