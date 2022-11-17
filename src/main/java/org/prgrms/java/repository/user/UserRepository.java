package org.prgrms.java.repository.user;

import org.prgrms.java.domain.user.User;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID userId, boolean isBlocked);

    Collection<User> findAll(boolean isBlocked);

    User insert(User user);

    long deleteAll();
}
