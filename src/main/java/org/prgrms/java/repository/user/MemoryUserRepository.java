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
    private final Map<UUID, User> black_storage = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findById(UUID userId, boolean isBlocked) {
        if (isBlocked) {
            return Optional.ofNullable(black_storage.get(userId));
        }
        return Optional.ofNullable(storage.get(userId));
    }

    @Override
    public Collection<User> findAll(boolean isBlocked) {
        if (isBlocked) {
            return black_storage.values();
        }
        return storage.values();
    }

    @Override
    public User insert(User user, boolean isBlocked) {
        if (findById(user.getUserId(), false).isPresent() || findById(user.getUserId(), true).isPresent()) {
            throw new UserException(String.format("Already exists user having id %s", user.getUserId()));
        }
        if (isBlocked) {
            black_storage.put(user.getUserId(), user);
            return black_storage.get(user.getUserId());
        }
        storage.put(user.getUserId(), user);
        return storage.get(user.getUserId());
    }
}
