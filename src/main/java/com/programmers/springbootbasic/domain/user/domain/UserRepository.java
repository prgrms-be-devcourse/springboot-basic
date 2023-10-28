package com.programmers.springbootbasic.domain.user.domain;

import com.programmers.springbootbasic.common.Repository;
import com.programmers.springbootbasic.domain.user.domain.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<Long, User> {

    List<User> findBlacklistedUsers();

    Optional<User> findByNickname(String nickname);
}
