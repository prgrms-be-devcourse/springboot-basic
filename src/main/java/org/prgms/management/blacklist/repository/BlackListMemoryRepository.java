package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BlackListMemoryRepository implements BlackListRepository {
    private final Map<UUID, Blacklist> blacklistMap = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Blacklist> getAll() {
        return blacklistMap;
    }

    @Override
    public Optional<Blacklist> getById(UUID blacklistId) {
        return Optional.ofNullable(blacklistMap.get(blacklistId));
    }

    @Override
    public Optional<Blacklist> getByCustomerId(UUID customerId) {
        return blacklistMap.values().stream()
                .filter(blacklist -> blacklist.getCustomerId() == customerId)
                .findFirst();
    }

    @Override
    public Optional<Blacklist> insert(Blacklist blacklist) {
        blacklistMap.put(blacklist.getBlacklistId(), blacklist);

        return Optional.of(blacklist);
    }

    @Override
    public Optional<Blacklist> update(Blacklist blacklist) {
        return Optional.ofNullable(
                blacklistMap.replace(blacklist.getBlacklistId(), blacklist));
    }

    @Override
    public Optional<Blacklist> delete(UUID blacklistId) {
        Optional<Blacklist> blacklist = Optional.ofNullable(blacklistMap.get(blacklistId));

        if (blacklist.isPresent()) {
            blacklistMap.remove(blacklistId);
            return blacklist;
        }

        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        blacklistMap.clear();
    }
}
