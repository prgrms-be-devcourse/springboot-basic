package com.example.voucherproject.user.repository;
import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.user.domain.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    List<User> getUserList(UserType type);
}
