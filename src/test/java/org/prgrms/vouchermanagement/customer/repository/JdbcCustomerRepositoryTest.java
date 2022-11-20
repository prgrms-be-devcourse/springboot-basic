package org.prgrms.vouchermanagement.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {

    @Autowired
    JdbcCustomerRepository jdbcCustomerRepository;
    @Autowired
    DataSource dataSource;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EmbeddedMysql embeddedMysql;

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.vouchermanagement.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-customer")
                    .username("test")
                    .password("test")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-customer", ScriptResolver.classPathScript("schema/create_table_ddl.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clear() {
        jdbcTemplate.update("DELETE FROM customers", Collections.EMPTY_MAP);
    }

    @Test
    @DisplayName("Customer 저장 성공")
    void save() {
        // given
        Customer customer = makeCustomer(UUID.randomUUID(), "name", "customer@google.com");

        // when
        jdbcCustomerRepository.save(customer);

        // then
        List<Customer> allCustomers = jdbcCustomerRepository.findAll();
        assertThat(allCustomers).hasSize(1);
        assertThat(customer)
                .usingRecursiveComparison()
                .isEqualTo(allCustomers.get(0));
    }

    @Test
    @DisplayName("모든 Customer 조회 성공")
    void findAll() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");
        List<Customer> customers = List.of(customer1, customer2);

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        List<Customer> allCustomers = jdbcCustomerRepository.findAll();

        // then
        assertThat(allCustomers).hasSize(2);
        assertThat(customers)
                .extracting("customerId", "name", "email")
                .contains(tuple(customer1.getCustomerId(), customer1.getName(), customer1.getEmail()),
                        tuple(customer2.getCustomerId(), customer2.getName(), customer2.getEmail()));
    }

    @Test
    @DisplayName("ID로 찾기 성공")
    void findByName() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        Customer findCustomer1 = jdbcCustomerRepository.findById(customer1.getCustomerId()).get();
        Customer findCustomer2 = jdbcCustomerRepository.findById(customer2.getCustomerId()).get();

        // then
        assertThat(findCustomer1)
                .usingRecursiveComparison()
                .isEqualTo(customer1);

        assertThat(findCustomer2)
                .usingRecursiveComparison()
                .isEqualTo(customer2);
    }

    @Test
    @DisplayName("찾는 Id가 없는 경우")
    void findByCustomerIdNotExist() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findById(UUID.randomUUID());

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("이름으로 찾기 성공")
    void findByCustomerId() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "name", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "name", "customer2@google.com");
        Customer customer3 = makeCustomer(UUID.randomUUID(), "nameA", "customer3@google.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);
        jdbcCustomerRepository.save(customer3);

        List<Customer> customers = List.of(customer1, customer2);

        // when
        List<Customer> allCustomers = jdbcCustomerRepository.findByName(customer1.getName());

        // then
        assertThat(allCustomers).hasSize(2);
        assertThat(customers)
                .extracting("customerId", "name", "email")
                .contains(tuple(customer1.getCustomerId(), customer1.getName(), customer1.getEmail()),
                        tuple(customer2.getCustomerId(), customer2.getName(), customer2.getEmail()));
    }

    @Test
    @DisplayName("찾는 이름이 없는 경우")
    void findByNameNotExist() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findById(UUID.randomUUID());

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("email로 찾기 성공")
    void findByEmail() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        Customer findCustomer1 = jdbcCustomerRepository.findByEmail(customer1.getEmail()).get();
        Customer findCustomer2 = jdbcCustomerRepository.findByEmail(customer2.getEmail()).get();

        // then
        assertThat(findCustomer1)
                .usingRecursiveComparison()
                .isEqualTo(customer1);

        assertThat(findCustomer2)
                .usingRecursiveComparison()
                .isEqualTo(customer2);
    }

    @Test
    @DisplayName("찾는 아이디가 없는 경우")
    void findByEmailNotExist() {
        // given
        Customer customer1 = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer customer2 = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");

        jdbcCustomerRepository.save(customer1);
        jdbcCustomerRepository.save(customer2);

        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail("None");

        // then
        assertThat(findCustomer).isEmpty();
    }

    @Test
    @DisplayName("수정 성공")
    void updateName() {
        // given
        Customer beforeChangeNameCustomer = makeCustomer(UUID.randomUUID(), "nameA", "customer1@google.com");
        Customer beforeChangeEmailCustomer = makeCustomer(UUID.randomUUID(), "nameB", "customer2@google.com");
        Customer beforeChangeBothCustomer = makeCustomer(UUID.randomUUID(), "nameC", "customer3@google.com");

        jdbcCustomerRepository.save(beforeChangeNameCustomer);
        jdbcCustomerRepository.save(beforeChangeEmailCustomer);
        jdbcCustomerRepository.save(beforeChangeBothCustomer);

        Customer afterChangeNameCustomer = makeCustomer(beforeChangeNameCustomer.getCustomerId(), "changeName", beforeChangeNameCustomer.getEmail());
        Customer afterChangeEmailCustomer = makeCustomer(beforeChangeEmailCustomer.getCustomerId(), beforeChangeEmailCustomer.getName(), "changeEmail@google.com");
        Customer afterChangeBothCustomer = makeCustomer(beforeChangeBothCustomer.getCustomerId(), "changeBothName", "changeBothEmail@google.com");

        // when
        jdbcCustomerRepository.update(afterChangeNameCustomer);
        jdbcCustomerRepository.update(afterChangeEmailCustomer);
        jdbcCustomerRepository.update(afterChangeBothCustomer);

        // then
        Customer resultChangeNameCustomer = jdbcCustomerRepository.findById(beforeChangeNameCustomer.getCustomerId()).get();
        Customer resultChangeEmailCustomer = jdbcCustomerRepository.findById(beforeChangeEmailCustomer.getCustomerId()).get();
        Customer resultChangeBothCustomer = jdbcCustomerRepository.findById(beforeChangeBothCustomer.getCustomerId()).get();

        assertThat(resultChangeNameCustomer)
                .usingRecursiveComparison()
                .isEqualTo(afterChangeNameCustomer);

        assertThat(resultChangeEmailCustomer)
                .usingRecursiveComparison()
                .isEqualTo(afterChangeEmailCustomer);

        assertThat(resultChangeBothCustomer)
                .usingRecursiveComparison()
                .isEqualTo(afterChangeBothCustomer);
    }

    @Test
    @DisplayName("이미 존재하는 이메일인 경우")
    void existEmail() {
        // given
        Customer customer = makeCustomer(UUID.randomUUID(), "name", "email@google.com");
        jdbcCustomerRepository.save(customer);

        // when
        boolean result = jdbcCustomerRepository.isPresent(customer.getEmail());

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("존재하지 않는 이메일인 경우")
    void notExistEmail() {
        // given
        Customer customer = makeCustomer(UUID.randomUUID(), "name", "email@google.com");

        // when
        boolean result = jdbcCustomerRepository.isPresent(customer.getEmail());

        // then
        assertFalse(result);
    }

    private Customer makeCustomer(UUID customerId, String name, String email) {
        return Customer.createNormalCustomer(customerId, name, email, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}