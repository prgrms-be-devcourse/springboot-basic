package com.programmers.springbootbasic.mock.util;

import com.programmers.springbootbasic.util.IdProvider;

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
