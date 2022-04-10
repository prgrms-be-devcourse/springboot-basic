package org.programmer.kdtspringboot.user;

import java.io.IOException;
import java.util.List;

public interface UserRepository {
    void saveUser(User user);
    List<User> findAll() throws IOException;
}
