package org.prgrms.java.repository.user;

import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<UUID, User> storage = new ConcurrentHashMap<>();
    private final Map<UUID, User> blackStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findById(UUID userId, boolean isBlocked) {
        if (isBlocked) {
            return Optional.ofNullable(blackStorage.get(userId));
        }
        return Optional.ofNullable(storage.get(userId));
    }

    @Override
    public Collection<User> findAll(boolean isBlocked) {
        if (isBlocked) {
            return blackStorage.values();
        }
        return storage.values();
    }

    @Override
    public User insert(User user) {
        if (findById(user.getUserId(), user.isBlocked()).isPresent()) {
            throw new UserException(String.format("Already exists user having id %s", user.getUserId()));
        }
        if (user.isBlocked()) {
            blackStorage.put(user.getUserId(), user);
            return blackStorage.get(user.getUserId());
        }
        storage.put(user.getUserId(), user);
        return storage.get(user.getUserId());
    }

    @Override
    public long deleteAll() {
        long count;
        count = storage.size() + blackStorage.size();

        storage.clear();
        blackStorage.clear();

        return count;
    }
}
