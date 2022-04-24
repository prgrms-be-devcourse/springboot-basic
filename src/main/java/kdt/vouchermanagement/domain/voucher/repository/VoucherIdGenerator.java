package kdt.vouchermanagement.domain.voucher.repository;

import java.util.concurrent.atomic.AtomicLong;

public class VoucherIdGenerator {

    private static final AtomicLong count = new AtomicLong(0L);

    public static Long idGenerate() {
        return count.incrementAndGet();
    }
}
