package org.programmers.customer.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.programmers.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(basePackages = {"org.programmers.customer.repository"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/homework")
                    .username("root")
                    .password("skyey9808")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public CustomerJdbcRepository customerNamedJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerJdbcRepository(namedParameterJdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    Customer testCustomer;

    @BeforeAll
    void setUp() {
        testCustomer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

    @AfterAll
    void cleaUp() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(1)
    void save() {
        customerJdbcRepository.save(testCustomer);
        Optional<Customer> findCustomerById = customerJdbcRepository.findById(testCustomer.getCustomerId());
        assertThat(findCustomerById.isEmpty(), is(false));
        assertThat(findCustomerById.get(), samePropertyValuesAs(testCustomer));
    }

    @Test
    @DisplayName("고객을 추가할 수 없다면, 런타임 에러가 발생한다.")
    @Order(2)
    void saveError() {
        Customer errorCustomer = new Customer(UUID.randomUUID(), "error-test", null, null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        assertThrows(RuntimeException.class, () -> customerJdbcRepository.save(errorCustomer));
    }

    @Test
    @DisplayName("전체 조회를 할 수 있다.")
    @Order(3)
    void findAll() {
        List<Customer> all = customerJdbcRepository.findAll();
        assertThat(all.isEmpty(), is(false));
        assertThat(all, hasSize(1));
    }

    @Test
    @DisplayName("이메일로 고객을 찾을 수 있다.")
    @Order(4)
    void findByEmail() {
        Optional<Customer> findCustomerByEmail = customerJdbcRepository.findByEmail(testCustomer.getEmail());
        assertThat(findCustomerByEmail.isEmpty(), is(false));
        assertThat(findCustomerByEmail.get(), samePropertyValuesAs(testCustomer));

    }

    @Test
    @DisplayName("이메일로 고객을 찾지 못하면 에러가 발생한다.")
    @Order(5)
    void findByEmailException() {
        String test_email = "test10@gmail.com";
        Optional<Customer> byEmail = customerJdbcRepository.findByEmail(test_email);
        assertThat(byEmail.isEmpty(), is(true));
    }


    @Test
    @DisplayName("ID로 고객을 찾을 수 있다.")
    @Order(6)
    void findById() {
        Optional<Customer> findCustomerById = customerJdbcRepository.findById(testCustomer.getCustomerId());
        assertThat(findCustomerById.isEmpty(), is(false));
        assertThat(findCustomerById.get(), samePropertyValuesAs(testCustomer));
    }

    @Test
    @DisplayName("ID로 고객을 찾지 못하면 예외가 발생한다.")
    @Order(7)
    void findByIdError() {
        Optional<Customer> byId = customerJdbcRepository.findById(UUID.randomUUID());
        assertThat(byId.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객의 이름을 수정할 수 있다.")
    @Order(8)
    void update() {
        String testName = "update-testName";
        Optional<Customer> findCustomerByEmail = customerJdbcRepository.findByEmail(testCustomer.getEmail());
        customerJdbcRepository.update(findCustomerByEmail.get().getEmail(), testName);

        Optional<Customer> updatedCustomerbyEmail = customerJdbcRepository.findByEmail(testCustomer.getEmail());
        assertThat(updatedCustomerbyEmail.get().getName(), is(testName));
    }

    @Test
    @DisplayName("업데이트 예외처리를 할 수 있다.")
    @Order(9)
    void updateException() {
        String test_email = "unknown-test@gmail.com";
        String test_name = "Lee";
        assertThrows(RuntimeException.class, () -> customerJdbcRepository.update(test_email, test_name));
    }

    @Test
    @DisplayName("이메일을 이용해서 고객을 삭제할 수 있다.")
    @Order(10)
    void deleteByEmail() {
        customerJdbcRepository.save(new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)));
        List<Customer> all = customerJdbcRepository.findAll();

        assertThat(all, hasSize(2));

        customerJdbcRepository.deleteByEmail("test2@gmail.com");
        List<Customer> all2 = customerJdbcRepository.findAll();
        Optional<Customer> byEmail = customerJdbcRepository.findByEmail("test2@gmail.com");

        assertThat(all2, hasSize(1));
        assertThat(byEmail.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 삭제를 못하게 되면 에러가 발생한다.")
    @Order(11)
    void deleteByEmailError() {
        assertThatThrownBy(() -> customerJdbcRepository.deleteByEmail("unknown-test@gmail.com"))
                .isInstanceOf(RuntimeException.class);
    }
    //        assertThrows(RuntimeException.class, () -> customerNamedJdbcRepository.deleteByEmail("sofas"));

    @Test
    @DisplayName("모든 고객을 삭제할 수 있다.")
    @Order(12)
    void deleteAll() {
        customerJdbcRepository.deleteAll();
        List<Customer> all = customerJdbcRepository.findAll();

        assertThat(all, hasSize(0));
    }
}