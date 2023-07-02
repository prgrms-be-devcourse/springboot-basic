package org.prgms.voucher.voucher.repository;

import org.prgms.voucher.voucher.AmountVoucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryAmountVoucherRepository implements AmountVoucherRepository {
    Map<UUID, AmountVoucher> memoryStorage = new HashMap<>();

    @Override
    public AmountVoucher save(AmountVoucher amountVoucher) {
        memoryStorage.put(amountVoucher.getId(), amountVoucher);
        return amountVoucher;
    }

    @Override
    public List<AmountVoucher> findAll() {
        List<AmountVoucher> vouchers = new ArrayList<>(memoryStorage.values());
        vouchers.sort(Comparator.comparing(AmountVoucher::getExpirationDate).reversed());

        return vouchers;
    }
}
