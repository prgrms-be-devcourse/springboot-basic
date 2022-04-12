package org.prgms.voucherProgram.repository.customer;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileCustomerRepositoryTest {

    @DisplayName("파일에 저장되어 있는 블랙리스트를 반환한다.")
    @Test
    void findBlackCustomers_ReturnBlackCustomers() {
        FileCustomerRepository fileCustomerRepository = new FileCustomerRepository("file/customer_blacklist.csv");
        assertThat(fileCustomerRepository.findBlackCustomers()).hasSize(3)
            .extracting("name").contains("hwan", "jin", "pobi");
    }

    @DisplayName("잘못된 파일일 경우 예외를 발생한다.")
    @Test
    void findBlackCustomers_WrongFile_ThrowException() {
        assertThatThrownBy(() -> new FileCustomerRepository("file/customer_black.csv"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 고객 파일이 아닙니다.");
    }
}
