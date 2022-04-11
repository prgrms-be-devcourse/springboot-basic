package org.prgms.voucherProgram.repository.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileUserRepositoryTest {

    @DisplayName("파일에 저장되어 있는 모든 blackUser를 List형으로 반환한다.")
    @Test
    void findBlackUsers_ReturnBlackUsers() {
        FileUserRepository fileUserRepository = new FileUserRepository("file/customer_blacklist.csv");
        assertThat(fileUserRepository.findBlackUsers()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
    }

    @DisplayName("잘못된 파일일 경우 예외를 발생한다.")
    @Test
    void findBlackUsers_WrongFile_ThrowException() {
        assertThatThrownBy(() -> new FileUserRepository("file/customer_black.csv"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 유저 파일이 아닙니다.");
    }
}
