package com.pgms.part1.util.keygen;

import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {
    private static Long key = 1L;

    public Long getKey() {
        return key++;
    }
}
