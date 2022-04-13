package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackListMemoryRepository implements BlackListRepository {
    private final Map<UUID, Blacklist> blacklistMap = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Blacklist> getAll() {
        return blacklistMap;
    }
}
