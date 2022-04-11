package com.example.voucherproject.user.repository;
import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.user.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserMemoryRepository implements UserRepository{
    private final Map<UUID, User> userMap = new HashMap<>();

    @Override
    public User save(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUserList(UserType type) {
        return null;
    }

}
