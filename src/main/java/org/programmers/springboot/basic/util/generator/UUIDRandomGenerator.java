package org.programmers.springboot.basic.util.generator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Primary
public class UUIDRandomGenerator implements UUIDGenerator {

    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
