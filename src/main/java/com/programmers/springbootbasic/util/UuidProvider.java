package com.programmers.springbootbasic.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidProvider {
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
