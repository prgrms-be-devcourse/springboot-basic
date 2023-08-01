package com.tangerine.voucher_system.application.global.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {

    private final UUID uuid;

    public IdGenerator() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

}
