package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VoucherMemoryStorage implements VoucherStorage {

    private Map<UUID, Voucher> voucherLinkedMap = new LinkedHashMap<>();

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return Optional.of(voucherLinkedMap.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherLinkedMap.put(voucher.getUUID(), voucher);
        return voucher;
    }
}
