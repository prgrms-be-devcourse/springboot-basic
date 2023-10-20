package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<User> findAll();

    List<User> findBlackAll();
}
