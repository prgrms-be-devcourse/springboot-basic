package com.programmers.springbootbasic.domain.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.programmers.springbootbasic.domain.user.domain.UserRepository;
import com.programmers.springbootbasic.domain.user.domain.entity.User;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("UserService 테스트")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("차단 처리가 되어 있는 사용자 목록을 가져 온다.")
    void success_findBlacklistedUsers() {
        // given
        User blockedUser = new User(1L, "user1", true);
        when(userRepository.findBlacklistedUsers()).thenReturn(List.of(blockedUser));

        // when
        List<UserResponse> result = userService.findBlacklistedUsers();

        // then
        assertThat(result).hasSize(1);
        assertThat(result)
            .extracting("nickname")
            .containsExactlyInAnyOrder("user1");
    }
}
