package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("default")
public class VoucherMemoryStorage implements VoucherStorage {

    private Map<UUID, Voucher> voucherLinkedMap = new LinkedHashMap<>();

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
