package org.programmer.kdtspringboot.user;

import java.util.List;

public interface UserRepository {
    void saveUser(User user);
    List<User> findAll();
}
