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

    Customer basicTestCustomer;

    @BeforeEach
    void setUp() {
        basicTestCustomer = new Customer(UUID.randomUUID(), "basic_test", "basic_test@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerJdbcRepository.save(basicTestCustomer);
    }

    @AfterEach
    void cleaUp() {
        customerJdbcRepository.deleteAll();
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    void save() {
        Customer testCustomer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com", null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        customerJdbcRepository.save(testCustomer);
        Optional<Customer> findCustomerById = customerJdbcRepository.findById(testCustomer.getCustomerId());
        assertThat(findCustomerById.isEmpty(), is(false));
        assertThat(findCustomerById.get(), samePropertyValuesAs(testCustomer));
    }

    @Test
    @DisplayName("고객을 추가할 수 없다면, 런타임 에러가 발생한다.")
    void saveError() {
        Customer errorCustomer = new Customer(UUID.randomUUID(), "error_test", null, null, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        assertThrows(RuntimeException.class, () -> customerJdbcRepository.save(errorCustomer));
    }

    @Test
    @DisplayName("전체 조회를 할 수 있다.")
    void findAll() {
        List<Customer> all = customerJdbcRepository.findAll();
        assertThat(all.isEmpty(), is(false));
        assertThat(all, hasSize(1));
    }

    @Test
    @DisplayName("이메일로 고객을 찾을 수 있다.")
    void findByEmail() {
        Optional<Customer> findCustomerByEmail = customerJdbcRepository.findByEmail(basicTestCustomer.getEmail());
        assertThat(findCustomerByEmail.isEmpty(), is(false));
        assertThat(findCustomerByEmail.get(), samePropertyValuesAs(basicTestCustomer));
    }

    @Test
    @DisplayName("이메일로 고객을 찾지 못하면 에러가 발생한다.")
    void findByEmailException() {
        String test_email = "error_test@gmail.com";
        Optional<Customer> byEmail = customerJdbcRepository.findByEmail(test_email);
        assertThat(byEmail.isEmpty(), is(true));
    }


    @Test
    @DisplayName("ID로 고객을 찾을 수 있다.")
    void findById() {
        Optional<Customer> findCustomerById = customerJdbcRepository.findById(basicTestCustomer.getCustomerId());
        assertThat(findCustomerById.isEmpty(), is(false));
        assertThat(findCustomerById.get(), samePropertyValuesAs(basicTestCustomer));
    }

    @Test
    @DisplayName("ID로 고객을 찾지 못하면 예외가 발생한다.")
    void findByIdError() {
        Optional<Customer> byId = customerJdbcRepository.findById(UUID.randomUUID());
        assertThat(byId.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객의 이름을 수정할 수 있다.")
    void update() {
        String testName = "update_testName";
        Optional<Customer> findCustomerByEmail = customerJdbcRepository.findByEmail(basicTestCustomer.getEmail());
        customerJdbcRepository.update(findCustomerByEmail.get().getEmail(), testName);

        Optional<Customer> updatedCustomerbyEmail = customerJdbcRepository.findByEmail(basicTestCustomer.getEmail());
        assertThat(updatedCustomerbyEmail.get().getName(), is(testName));
    }

    @Test
    @DisplayName("업데이트가 정상적으로 처리되지 않을 경우, 예외처리를 할 수 있다.")
    void updateException() {
        String test_email = "unknown_test@gmail.com";
        String test_name = "Lee";
        assertThrows(RuntimeException.class, () -> customerJdbcRepository.update(test_email, test_name));
    }

    @Test
    @DisplayName("이메일을 이용해서 고객을 삭제할 수 있다.")
    void deleteByEmail() {
        List<Customer> all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(1));

        customerJdbcRepository.deleteByEmail("basic_test@gmail.com");

        List<Customer> all2 = customerJdbcRepository.findAll();
        Optional<Customer> byEmail = customerJdbcRepository.findByEmail("test2@gmail.com");
        assertThat(all2, hasSize(0));
        assertThat(byEmail.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 삭제를 못하게 되면 에러가 발생한다.")
    void deleteByEmailError() {
        assertThatThrownBy(() -> customerJdbcRepository.deleteByEmail("unknown_test@gmail.com"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("모든 고객을 삭제할 수 있다.")
    void deleteAll() {
        customerJdbcRepository.deleteAll();
        List<Customer> all = customerJdbcRepository.findAll();
        assertThat(all, hasSize(0));
    }
}