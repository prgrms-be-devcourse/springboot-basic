package com.programmers.springbootbasic.mock.util;

import com.programmers.springbootbasic.util.IdProvider;

import java.util.UUID;

public class MockUuidProvider implements IdProvider<UUID> {
    public UUID generateId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }
}
