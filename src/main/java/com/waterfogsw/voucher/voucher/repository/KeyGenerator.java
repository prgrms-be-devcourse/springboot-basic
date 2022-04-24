package com.waterfogsw.voucher.voucher.repository;

import java.util.concurrent.atomic.AtomicLong;

public class KeyGenerator {
    private static final AtomicLong count = new AtomicLong(0L);

    public static Long keyGenerate() {
        return count.incrementAndGet();
    }
}
