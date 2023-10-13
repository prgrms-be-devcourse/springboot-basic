package com.programmers.vouchermanagement.mock.util;

import com.programmers.vouchermanagement.util.IdProvider;

import java.util.UUID;

public class MockUuidProvider implements IdProvider<UUID> {
    private final UUID fixture;

    public MockUuidProvider(UUID fixture) {
        this.fixture = fixture;
    }
    public UUID generateId() {
        return fixture;
    }
}
