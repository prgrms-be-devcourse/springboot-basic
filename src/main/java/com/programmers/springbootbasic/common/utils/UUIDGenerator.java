package com.programmers.springbootbasic.common.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@NoArgsConstructor
public class UUIDGenerator implements UUIDValueStrategy {
    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
