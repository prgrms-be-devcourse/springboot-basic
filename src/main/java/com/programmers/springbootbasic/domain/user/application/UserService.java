package com.programmers.springbootbasic.domain.user.application;

import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> findBlacklistedUsers() {
        return userRepository.findBlacklistedUsers()
            .stream()
            .map(UserResponse::of)
            .toList();
    }

}
