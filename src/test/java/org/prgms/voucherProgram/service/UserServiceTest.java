package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.entity.user.User;

class UserServiceTest {
    UserService userService = new UserService(() -> Arrays.asList(new User(UUID.randomUUID(), "jin"),
        new User(UUID.randomUUID(), "hwan"),
        new User(UUID.randomUUID(), "pobi")));

    @DisplayName("모든 유저를 반환한다.")
    @Test
    void findBlackList_ReturnBlackUsers() {
        assertThat(userService.findBlackList()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
    }
}
