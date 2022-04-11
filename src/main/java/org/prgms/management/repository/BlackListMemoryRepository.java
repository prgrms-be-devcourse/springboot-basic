package org.prgms.management.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackListMemoryRepository implements BlackListRepository {
    private final Map<UUID, String> blacklistMap = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, String> getAll() {
        return blacklistMap;
    }
}
