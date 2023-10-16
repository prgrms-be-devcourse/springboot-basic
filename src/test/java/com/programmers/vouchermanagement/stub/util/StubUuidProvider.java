package com.programmers.vouchermanagement.stub.util;

import com.programmers.vouchermanagement.util.IdProvider;

import java.util.UUID;

public class StubUuidProvider implements IdProvider<UUID> {
    private final UUID fixture;

    public StubUuidProvider(UUID fixture) {
        this.fixture = fixture;
    }
    public UUID generateId() {
        return fixture;
    }
}
