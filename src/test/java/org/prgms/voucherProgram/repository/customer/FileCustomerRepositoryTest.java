package org.prgms.voucherProgram.repository.customer;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.entity.customer.Email;
import org.prgms.voucherProgram.entity.customer.Name;

class FileCustomerRepositoryTest {

    @DisplayName("파일에 저장되어 있는 블랙리스트를 반환한다.")
    @Test
    void findBlackCustomers_ReturnBlackCustomers() {
        FileCustomerRepository fileCustomerRepository = new FileCustomerRepository("file/customer_blacklist.csv");
        assertThat(fileCustomerRepository.findBlackCustomers()).hasSize(3)
            .extracting("name", "email").contains(tuple(new Name("hwan"), new Email("hwan@gmail.com")),
                tuple(new Name("pobi"), new Email("pobi@gmail.com")),
                tuple(new Name("jin"), new Email("jin@gmail.com")));
    }

    @DisplayName("잘못된 파일일 경우 예외를 발생한다.")
    @Test
    void findBlackCustomers_WrongFile_ThrowException() {
        assertThatThrownBy(() -> new FileCustomerRepository("file/customer_black.csv"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 고객 파일이 아닙니다.");
    }
}
