package com.programmers.springbootbasic.domain.user.application;

import static com.programmers.springbootbasic.exception.ErrorCode.DUPLICATED_USER;

import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.presentation.dto.CreateUserRequest;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.exception.exceptionClass.UserException;
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

    @Transactional
    public Long create(CreateUserRequest request) {
        // 유효성 검사
        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new UserException(DUPLICATED_USER);
        }
        var result = userRepository.save(request.toEntity());
        return result.getId();
    }

    public List<UserResponse> findBlacklistedUsers() {
        return userRepository.findBlacklistedUsers()
            .stream()
            .map(UserResponse::of)
            .toList();
    }

}
