package com.pgms.part1.util.keygen;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class KeyGenerator {
    private final AtomicLong key;

    public KeyGenerator() {
        key = new AtomicLong(1L);
    }

    public Long getKey() {
        long currentKey = key.get();
        key.set(currentKey+1);
        return currentKey;
    }
}
