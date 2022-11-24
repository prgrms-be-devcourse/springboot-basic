package org.prgrms.java.repository.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {
    private static final DataSource dataSource = DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:2215/test-voucher_mgmt")
            .username("test")
            .password("test1234!")
            .type(HikariDataSource.class)
            .build();

    private static final NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    private static final CustomerRepository customerRepository = new JdbcCustomerRepository(namedParameterJdbcTemplate);

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = MysqldConfig.aMysqldConfig(Version.v8_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("정상/블랙 유저를 등록할 수 있다.")
    void testInsert() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now(), true);

        Customer insertedCustomer = customerRepository.insert(customer);
        Customer insertedBlockedCustomer = customerRepository.insert(blockedCustomer);

        assertThat(customer, samePropertyValuesAs(insertedCustomer));
        assertThat(blockedCustomer, samePropertyValuesAs(insertedBlockedCustomer));
    }

    @Test
    @DisplayName("동일한 ID의 유저는 등록할 수 없다.")
    void testInsertSameIdCustomer() {
        assertThrows(CustomerException.class, () -> {
            UUID customerId = UUID.randomUUID();
            Customer customer = new Customer(customerId, "test", "test@gmail.com", LocalDateTime.now());
            Customer customer2 = new Customer(customerId, "test2", "test2@gmail.com", LocalDateTime.now());

            customerRepository.insert(customer);
            customerRepository.insert(customer2);
        });
    }

    @Test
    @DisplayName("등록한 유저를 ID, 이름, 이메일로 찾을 수 있다.")
    void testFindById() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now());
        Customer customer3 = new Customer(UUID.randomUUID(), "test3", "test3@gmail.com", LocalDateTime.now());

        customerRepository.insert(customer);
        customerRepository.insert(customer2);
        customerRepository.insert(customer3);

        assertThat(customerRepository.findById(customer.getCustomerId()).get(), samePropertyValuesAs(customer));
        assertThat(customerRepository.findByName(customer2.getName()).get(), samePropertyValuesAs(customer2));
        assertThat(customerRepository.findByEmail(customer3.getEmail()).get(), samePropertyValuesAs((customer3)));
    }

    @Test
    @DisplayName("등록한 유저와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now());

        customerRepository.insert(customer);
        customerRepository.insert(customer2);

        assertThat(customerRepository.findAll().isEmpty(), is(false));
        assertThat(customerRepository.findAll(), hasSize(2));
    }

    @Test
    @DisplayName("유저를 등록한 후에 필드를 변경할 수 있다.")
    void testUpdate() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        customerRepository.insert(customer);

        Customer updatedCustomer = new Customer(customer.getCustomerId(), "update-test", customer.getEmail(), customer.getCreatedAt());
        Customer insertedUpdatedCustomer = customerRepository.update(updatedCustomer);

        assertThat(customer, not(samePropertyValuesAs(insertedUpdatedCustomer)));
    }

    @Test
    @DisplayName("등록한 유저와 전체 삭제한 개수가 같다.")
    void testDeleteAll() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", LocalDateTime.now());
        Customer blackCustomer = new Customer(UUID.randomUUID(), "unknown", "spam@spam.com", LocalDateTime.now(), true);

        customerRepository.insert(customer);
        customerRepository.insert(customer2);
        customerRepository.insert(blackCustomer);

        assertThat(customerRepository.deleteAll(), is(3L));
    }
}