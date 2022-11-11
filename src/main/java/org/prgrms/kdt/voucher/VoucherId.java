package org.prgrms.kdt.voucher;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class VoucherId {
    private static final AtomicLong voucherId = new AtomicLong(0);

    public static long increase() {
        return voucherId.incrementAndGet();
    }
}
