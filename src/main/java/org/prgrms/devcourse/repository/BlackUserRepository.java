package org.prgrms.devcourse.repository;

import org.prgrms.devcourse.domain.BlackUser;

import java.util.List;
import java.util.Optional;

public interface BlackUserRepository {
    Optional<BlackUser> findByName(String name);
    List<BlackUser> findAll();
}
