package com.programmers.springbootbasic.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidProvider implements IdProvider<UUID> {
    @Override
    public UUID generateId() {
        return UUID.randomUUID();
    }
}
