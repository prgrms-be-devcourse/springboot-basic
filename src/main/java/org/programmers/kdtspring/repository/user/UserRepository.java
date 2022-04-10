package org.programmers.kdtspring.repository.user;

import org.programmers.kdtspring.entity.user.BlackListedUser;
import org.programmers.kdtspring.entity.user.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    void saveBlackUser(User user);

    List<String[]> findAll();

}
