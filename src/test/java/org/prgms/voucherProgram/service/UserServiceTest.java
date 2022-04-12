package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.entity.user.User;
import org.prgms.voucherProgram.repository.user.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @DisplayName("모든 블랙리스트를 반환한다.")
    @Test
    void findBlackList_ReturnBlackUsers() {
        List<User> mockBlackUsers = Arrays.asList(new User(UUID.randomUUID(), "jin"),
            new User(UUID.randomUUID(), "hwan"),
            new User(UUID.randomUUID(), "pobi"));

        given(userRepository.findBlackUsers()).willReturn(mockBlackUsers);

        assertThat(userService.findBlackList()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
        then(userRepository).should(times(1)).findBlackUsers();
    }
}
