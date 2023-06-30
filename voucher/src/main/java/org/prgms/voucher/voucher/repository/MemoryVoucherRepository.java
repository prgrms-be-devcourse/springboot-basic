package org.prgms.voucher.voucher.repository;

import org.prgms.voucher.voucher.AmountVoucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    Map<UUID, AmountVoucher> memoryStorage = new HashMap<>();

    @Override
    public AmountVoucher save(AmountVoucher amountVoucher) {
        memoryStorage.put(amountVoucher.getId(), amountVoucher);
        return amountVoucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>(memoryStorage.values());
        vouchers.sort(Comparator.comparing(Voucher::getExpirationDate).reversed()
                .thenComparing(Voucher::getCreatedAt));
        return vouchers;
    }
}
