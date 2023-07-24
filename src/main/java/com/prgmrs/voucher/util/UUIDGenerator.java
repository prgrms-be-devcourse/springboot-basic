package com.prgmrs.voucher.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator implements IdGenerator {
    public UUID generate() {
        return UUID.randomUUID();
    }
}
