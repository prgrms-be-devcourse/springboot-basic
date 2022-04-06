package org.prgms.voucheradmin.domain.voucher.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getAll() {
        List<Voucher> vouchers = storage.keySet().stream()
                .map(storage::get)
                .collect(Collectors.toList());

        return vouchers;
    }
}
