package org.prgrms.kdt.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.model.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    EmbeddedMysql embeddedMysql;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer newCustomer;

    @BeforeAll
    void clean() {
        newCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        var mysqldConfig = aMysqldConfig(Version.v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @Test
    @Order(1)
    @DisplayName("고객을 추가 할 수 있다.")
    void testInsert() {
        customerJdbcRepository.insertCustomer(newCustomer);

        Optional<Customer> receiveCustomer = customerJdbcRepository.findCustomerById(newCustomer.getCustomerId());
        assertThat(receiveCustomer.isEmpty(), is(false));
        assertThat(receiveCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("이메일이 중복된 고객은 추가 할 수 없다.")
    void testInsertDuplicateCustomer() {
        //given
        Customer newCustomer2 = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        assertThrows(DuplicateKeyException.class, () -> customerJdbcRepository.insertCustomer(newCustomer2));
    }

    @Test
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void testFindAll() {
        var customers = customerJdbcRepository.findAllCustomer();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    void testFindById() {
        var customerId = newCustomer.getCustomerId();
        Optional<Customer> receiveCustomer = customerJdbcRepository.findCustomerById(customerId);

        assertThat(receiveCustomer, notNullValue());
        assertThat(receiveCustomer.get().getCustomerId(), equalTo(customerId));
    }
}