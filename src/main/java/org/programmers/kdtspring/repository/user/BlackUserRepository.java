package org.programmers.kdtspring.repository.user;

import org.programmers.kdtspring.entity.user.User;

import java.util.List;

public interface BlackUserRepository {

    void saveBlackUser(User user);

    List<String[]> findAll();

}