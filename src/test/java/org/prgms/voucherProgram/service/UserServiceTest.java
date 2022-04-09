package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.entity.user.User;
import org.prgms.voucherProgram.repository.user.UserRepository;

class UserServiceTest {
    UserService userService = new UserService(new UserRepository() {
        @Override
        public List<User> findAll() {
            return Arrays.asList(new User(UUID.randomUUID(), "jin"),
                new User(UUID.randomUUID(), "hwan"),
                new User(UUID.randomUUID(), "pobi"));
        }
    });

    @DisplayName("모든 유저를 반환한다.")
    @Test
    void findAll_ReturnAllVoucher() {
        assertThat(userService.findBlackList()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
    }
}
