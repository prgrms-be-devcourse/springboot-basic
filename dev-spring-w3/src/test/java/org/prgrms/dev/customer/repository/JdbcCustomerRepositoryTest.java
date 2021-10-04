package org.prgrms.dev.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import org.junit.jupiter.api.*;
import org.prgrms.dev.config.DBConfig;
import org.prgrms.dev.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.prgrms.dev.config.DBConfig.dbSetup;

@SpringJUnitConfig
@ContextConfiguration(classes = {DBConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "dev")
class JdbcCustomerRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;

    @BeforeAll
    static void setup() {
        embeddedMysql = dbSetup();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("고객을 추가할 수 있다.")
    @Test
    void createTest() {
        Customer customer = new Customer(UUID.randomUUID(), "tester03", "test03@gmail.com", LocalDateTime.now());

        jdbcCustomerRepository.create(customer);

        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get().getName()).isEqualTo(customer.getName());
    }

    @DisplayName("중복된 이메일로 고객을 추가할 수 없다.")
    @Test
    void createByDuplicateEmailTest() {
        Customer customer = new Customer(UUID.randomUUID(), "test01", "test01@gmail.com", LocalDateTime.now());

        assertThatThrownBy(() -> jdbcCustomerRepository.create(customer))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("This email already exists");
    }

    @DisplayName("이메일로 고객 정보를 조회할 수 있다.")
    @Test
    void findByEmailTest() {
        String email = "test02@gmail.com";

        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findByEmail(email);

        assertThat(retrievedCustomer.orElse(null)).isNotNull();
    }
}
