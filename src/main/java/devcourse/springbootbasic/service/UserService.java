package devcourse.springbootbasic.service;

import devcourse.springbootbasic.dto.UserFindResponse;
import devcourse.springbootbasic.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserFindResponse> findAllBlacklistedUsers() {
        return userRepository.findAllBlacklistedUsers()
                .stream()
                .map(UserFindResponse::new)
                .toList();
    }
}
