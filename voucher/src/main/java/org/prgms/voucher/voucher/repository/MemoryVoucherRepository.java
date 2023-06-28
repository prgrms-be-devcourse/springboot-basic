package org.prgms.voucher.voucher.repository;

import org.prgms.voucher.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    Map<UUID, Voucher> memoryStorage = new HashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        memoryStorage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>(memoryStorage.values());
        vouchers.sort(Comparator.comparing(Voucher::getExpirationDate).reversed()
                .thenComparing(Voucher::getCreatedAt));
        return vouchers;
    }
}
