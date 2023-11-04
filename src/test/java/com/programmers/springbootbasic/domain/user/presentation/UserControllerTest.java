package com.programmers.springbootbasic.domain.user.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.programmers.springbootbasic.domain.user.application.UserService;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("UserController 테스트")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("블랙리스트에 등록 된 유저가 반환 된다.")
    @Test
    void success_getBlackList() {
        // given
        List<UserResponse> mockBlacklist = List.of(
            new UserResponse("User1"),
            new UserResponse("User2")
        );
        when(userService.findBlacklistedUsers()).thenReturn(mockBlacklist);

        // when
        List<UserResponse> resultBlacklist = userController.getBlackList();

        // then
        assertEquals(mockBlacklist, resultBlacklist);
    }
}
