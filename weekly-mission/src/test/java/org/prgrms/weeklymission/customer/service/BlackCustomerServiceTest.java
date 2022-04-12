package org.prgrms.weeklymission.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.weeklymission.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.weeklymission.customer.domain.CustomerType.BLACK;

@SpringBootTest
@ActiveProfiles("local")
class BlackCustomerServiceTest {
    private final UUID customerId = UUID.randomUUID();
    private final String name = "kim";
    @Autowired
    private BlackCustomerService service;

    @BeforeEach
    void beforeEach() {
        service.deleteData();
    }

    @Test
    @DisplayName("블랙 회원 저장")
    void test_black_save() {
        Customer customer = service.registerBlackCustomer(customerId, name);

        assertThat(customer).isNotNull();
        assertThat(customer.getCustomerType()).isEqualTo(BLACK);
        assertThat(service.countBlackCustomer()).isEqualTo(1);
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
        assertThrows(RuntimeException.class, () -> service.findBlackCustomerById(UUID.randomUUID()));

    }

    @Test
    @DisplayName("데이터 삭제 후 블랙 회원의 수는 0이 되어야 한다.")
    void test_afterClear_countZero() {
        service.registerBlackCustomer(customerId, name);
        service.deleteData();
        assertThat(service.countBlackCustomer()).isEqualTo(0);
    }
}