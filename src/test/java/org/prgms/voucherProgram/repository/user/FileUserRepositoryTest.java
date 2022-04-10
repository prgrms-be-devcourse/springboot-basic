package org.prgms.voucherProgram.repository.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class FileUserRepositoryTest {

    FileUserRepository fileUserRepository = new FileUserRepository(
        new ClassPathResource("file/customer_blacklist.csv"));

    @DisplayName("파일에 저장되어 있는 모든 User를 List형으로 반환한다.")
    @Test
    void findBlackUsers_ReturnBlackUsers() {
        Assertions.assertThat(fileUserRepository.findBlackUsers()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
    }
}
