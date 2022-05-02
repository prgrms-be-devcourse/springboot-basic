package org.prgms.voucherProgram.domain.customer.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.customer.exception.WrongFileException;
import org.prgms.voucherProgram.customer.repository.BlackListRepository;

class BlackListRepositoryTest {

    @DisplayName("파일에 저장되어 있는 블랙리스트를 반환한다.")
    @Test
    void findBlackCustomers_ReturnBlackCustomers() {
        BlackListRepository blackListRepository = new BlackListRepository("file/customer_blacklist.csv");
        assertThat(blackListRepository.findBlackCustomers()).hasSize(3)
            .extracting("name", "email").contains(tuple("hwan", "hwan@gmail.com"),
                tuple("pobi", "pobi@gmail.com"),
                tuple("jin", "jin@gmail.com"));
    }

    @DisplayName("잘못된 파일일 경우 예외를 발생한다.")
    @Test
    void findBlackCustomers_WrongFile_ThrowException() {
        assertThatThrownBy(() -> new BlackListRepository("file/customer_black.csv"))
            .isInstanceOf(WrongFileException.class)
            .hasMessage("[ERROR] 올바른 파일이 아닙니다.");
    }
}
