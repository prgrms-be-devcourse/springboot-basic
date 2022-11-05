package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryVoucherManager implements VoucherManager {

    private final Map<Long, Voucher> vouchers = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(sequence++, voucher);
        return voucher;
    }
}
