package org.prgrms.kdt.repository.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.domain.user.User;
import org.prgrms.kdt.io.file.CsvFileIO;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileUserRepositoryTest {
    UserRepository userRepository = new FileUserRepository(new CsvFileIO("customer_blacklist.csv"));

    FileUserRepositoryTest() throws IOException {
    }

    @Test
    @DisplayName("모든 사용자를 가져온다")
    public void findAll() throws Exception {
        // given

        // when
        List<User> all = userRepository.findAll();

        // then
        assertThat(all.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("블랙리스트 사용자들을 가져온다")
    public void findBlackListedUsers() throws Exception {
        // given

        // when
        List<User> all = userRepository.findBlackListedUsers();

        // then
        assertThat(all.size()).isEqualTo(1);
    }
}