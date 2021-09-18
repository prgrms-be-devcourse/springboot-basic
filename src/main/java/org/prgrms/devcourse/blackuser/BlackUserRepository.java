package org.prgrms.devcourse.blackuser;

import java.util.List;
import java.util.Optional;

public interface BlackUserRepository {
    Optional<BlackUser> findByName(String name);
    List<BlackUser> findAll();
}
