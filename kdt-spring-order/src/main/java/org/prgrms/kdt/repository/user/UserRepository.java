package org.prgrms.kdt.repository.user;

import org.prgrms.kdt.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findBlackListedUsers();
}
