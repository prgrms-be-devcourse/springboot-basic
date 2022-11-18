package org.prgrms.java.service.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.java.domain.customer.Customer;
import org.prgrms.java.exception.CustomerException;
import org.prgrms.java.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//@SpringBootTest
@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.java.*.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

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
                .addSchema("voucher_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @BeforeEach
    @AfterEach
    void clean() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("서비스를 통해 정상/블랙 유저를 등록할 수 있다.")
    void testCreateCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        Customer insertedCustomer = customerService.createCustomer(customer);
        Customer insertedBlockedCustomer = customerService.createCustomer(blockedCustomer);

        assertThat(customer, samePropertyValuesAs(insertedCustomer));
        assertThat(blockedCustomer, samePropertyValuesAs(insertedBlockedCustomer));
    }

    @Test
    @DisplayName("ID 같은 유저를 둘 이상 등록할 수 없다.")
    void testCreateCustomersWithDuplicatedId() {
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "test", "test@gmail.com");
        Customer blockedCustomer = new Customer(customerId, "test2", "test2@gmail.com", true);

        Assertions.assertThrows(CustomerException.class, () -> {
            customerService.createCustomer(customer);
            customerService.createCustomer(blockedCustomer);
        });
    }

    @Test
    @DisplayName("서비스를 통해 정상 유저를 조회할 수 있다.")
    void testGetCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");

        customerService.createCustomer(customer);

        assertThat(customerService.getCustomer(customer.getCustomerId()), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("서비스를 통해 블랙 유저를 조회할 수 있다.")
    void testGetBlackCustomer() {
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", true);

        customerService.createCustomer(blockedCustomer);

        assertThat(customerService.getBlackCustomer(blockedCustomer.getCustomerId()), samePropertyValuesAs(blockedCustomer));
    }

    @Test
    @DisplayName("존재하지 않는 정상/블랙 유저 ID로 조회하면 예외가 발생한다.")
    void testGetNonExistCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        customerService.createCustomer(customer);
        customerService.createCustomer(blockedCustomer);

        Assertions.assertThrows(CustomerException.class, () -> {
            customerService.getCustomer(UUID.randomUUID());
            customerService.getBlackCustomer(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("유저를 등록하지 않으면 전체 조회시 빈 컬렉션이 반환된다.")
    void testGetAllCustomersWithNoCreation() {
        assertThat(customerService.getAllCustomers(), hasSize(0));
        assertThat(customerService.getAllBlackCustomers(), hasSize(0));
    }

    @Test
    @DisplayName("서비스를 통해 모든 일반 유저를 조회할 수 있다.")
    void testGetAllCustomers() {
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com");
        Customer customer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com");

        customerService.createCustomer(customer);
        customerService.createCustomer(customer2);

        assertThat(customerService.getAllCustomers(), hasSize(2));
        assertThat(customerService.getAllCustomers(), containsInAnyOrder(samePropertyValuesAs(customer), samePropertyValuesAs(customer2)));
    }

    @Test
    @DisplayName("서비스를 통해 모든 블랙 유저를 조회할 수 있다.")
    void testGetAllBlackCustomers() {
        Customer blockedCustomer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", true);
        Customer blockedCustomer2 = new Customer(UUID.randomUUID(), "test2", "test2@gmail.com", true);

        customerService.createCustomer(blockedCustomer);
        customerService.createCustomer(blockedCustomer2);

        assertThat(customerService.getAllBlackCustomers(), hasSize(2));
        assertThat(customerService.getAllBlackCustomers(), containsInAnyOrder(samePropertyValuesAs(blockedCustomer), samePropertyValuesAs(blockedCustomer2)));
    }
}
