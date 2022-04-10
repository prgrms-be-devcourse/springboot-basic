package org.prgrms.weeklymission.customer.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.weeklymission.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.weeklymission.customer.domain.CustomerType.BLACK;

@SpringBootTest
@ActiveProfiles("local")
class BlackCustomerServiceTest {
    private final String customerId = "abc123";
    private final String name = "kim";
    private final BlackCustomerService service;

    @Autowired
    BlackCustomerServiceTest(BlackCustomerService service) {
        this.service = service;
    }

    @Test
    @DisplayName("블랙 회원 저장")
    void test_black_save() {
        System.out.println("hello");
        Customer customer = service.registerBlackCustomer(customerId, name);

        assertThat(customer).isNotNull();
        assertThat(customer.getCustomerType()).isEqualTo(BLACK);
        assertThat(service.getBlackCustomerCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("블랙 회원 찾기")
    void test_black_find() {
        Customer customer = service.registerBlackCustomer(customerId, name);
        Customer findCustomer = service.findBlackCustomerById(customerId);

        assertThat(customer.getName()).isEqualTo(findCustomer.getName());
    }

    @Test
    @DisplayName("존재하지 않는 블랙 회원 찾을 경우")
    void test_black_not_found() {
        assertThrows(RuntimeException.class, () -> service.findBlackCustomerById("no_id"));

    }

    @Test
    @DisplayName("데이터 삭제 후 블랙 회원의 수는 0이 되어야 한다.")
    void test_afterClear_countZero() {
        service.registerBlackCustomer(customerId, name);
        service.deleteData();
        assertThat(service.getBlackCustomerCount()).isEqualTo(0);
    }
}