package com.blessing333.springbasic.domain.customer.service;

import com.blessing333.springbasic.domain.customer.model.Customer;
import com.blessing333.springbasic.domain.customer.repository.CustomerRepository;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceTest {
    @Autowired
    CustomerRepository repository;
    @Autowired
    CustomerService service;
    @Autowired
    DataSource dataSource;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-customer", classPathScript("customer.sql"))
                .start();
    }

    @AfterEach
    void resetDB() {
        repository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @DisplayName("새로운 고객을 생성할 수 있어야 한다.")
    @Test
    void registerCustomerTest() {
        Customer customer = createCustomer("test", "test@email.com");
        Customer newCustomer = service.registerCustomer(customer);
        Optional<Customer> byId = repository.findById(newCustomer.getCustomerId());

        assertTrue(byId.isPresent());
        Customer found = byId.get();
        Assertions.assertThat(found.getName()).isEqualTo("test");
        Assertions.assertThat(found.getEmail()).isEqualTo("test@email.com");
    }

    @DisplayName("고객 정보를 조회할 수 있어야 한다.")
    @Test
    void inquiryCustomerInformationTest() {
        Customer customer = createCustomer("test", "test@email.com");
        Customer newCustomer = service.registerCustomer(customer);

        Customer found = service.inquiryCustomerInformation(newCustomer.getCustomerId());

        Assertions.assertThat(found.getCustomerId()).isEqualTo(newCustomer.getCustomerId());
        Assertions.assertThat(found.getName()).isEqualTo(newCustomer.getName());
        Assertions.assertThat(found.getEmail()).isEqualTo(newCustomer.getEmail());
        Assertions.assertThat(found.getCreatedAt()).isEqualTo(newCustomer.getCreatedAt());

    }

    @DisplayName("등록되지 않은 고객 조회를 요청할 경우 IllegalArgumentException 발생시킨다.")
    @Test
    void inquiryInvalidCustomerInformationTest() {
        UUID invalidId = UUID.randomUUID();

        assertThrows(IllegalArgumentException.class, () -> service.inquiryCustomerInformation(invalidId));
    }

    @DisplayName("등록된 모든 고객 정보를 조회할 수 있어야 한다.")
    @Test
    void loadAllCustomerInformation() {
        Customer firstCustomer = createCustomer("test", "test@email.com");
        Customer secondCustomer = createCustomer("test", "test2@email.com");
        Customer thirdCustomer = createCustomer("test", "test3@email.com");
        service.registerCustomer(firstCustomer);
        service.registerCustomer(secondCustomer);
        service.registerCustomer(thirdCustomer);

        List<Customer> customerList = service.loadAllCustomerInformation();

        Assertions.assertThat(customerList).hasSize(3).contains(firstCustomer, secondCustomer, thirdCustomer);
    }

    private Customer createCustomer(String name, String email) {
        return Customer.customerBuilder()
                .name(name)
                .email(email)
                .build();
    }

    @Configuration
    @ComponentScan("com.blessing333.springbasic.domain.customer")
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-customer")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

}
