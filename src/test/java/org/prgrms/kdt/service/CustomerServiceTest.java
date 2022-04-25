package org.prgrms.kdt.service;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.TestConfiguration;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.repository.CustomerJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    CustomerService customerService;

    Customer newCustomer;

    @BeforeAll
    void clean() {
        TestConfiguration.clean(embeddedMysql);
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        customerService.createCustomer(newCustomer);
    }

    @Test
    @DisplayName("이메일이 중복된 고객은 추가 할 수 없다.")
    void joinDuplicateCustomer() {
        //given
        Customer newCustomer2 = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        int beforeJoinSize = customerJdbcRepository.findAllCustomer().size();
        customerService.createCustomer(newCustomer2);

        //when
        int afterJoinSize = customerJdbcRepository.findAllCustomer().size();

        //then
        assertThat(afterJoinSize, equalTo(beforeJoinSize));
    }
}