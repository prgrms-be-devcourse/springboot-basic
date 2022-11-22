package org.prgrms.kdt.voucher;

import java.util.concurrent.atomic.AtomicLong;

public final class InMemoryVoucherId {
    private static final AtomicLong voucherId = new AtomicLong(0);

    private InMemoryVoucherId() {
    }

    public static long increase() {
        return voucherId.incrementAndGet();
    }

    public static long getId() {
        return voucherId.get();
    }
}
