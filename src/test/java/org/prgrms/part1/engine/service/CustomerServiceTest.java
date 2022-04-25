package org.prgrms.part1.engine.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.repository.CustomerRepository;
import org.prgrms.part1.engine.repository.JdbcCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.part1.engine", "org.prgrms.part1.exception"})
    @EnableTransactionManagement
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("1q2w3e4r!")
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
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerService(customerRepository);
        }
    }
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1q2w3e4r!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @AfterEach
    void cleanTable() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Customer를 생성할 수 있다.")
    public void testCreateCustomer() {
        Customer customer = customerService.createCustomer("test0", "test0@gmail.com");
        Customer findCustomer = customerService.getCustomerById(customer.getCustomerId());

        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("생성한 Customer를 DB에 삽입할 수 있다.")
    public void testInsertCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com", LocalDateTime.now().withNano(0));
        customerService.insertCustomer(customer);
        Customer findCustomer = customerService.getCustomerById(customer.getCustomerId());

        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("DB에서 Customer목록을 불러올 수 있다.")
    public void testGetAllCustomers() {
        Customer customer0 = customerService.createCustomer("test0", "test0@gmail.com");

        Customer customer1 = customerService.createCustomer("test1", "test1@gmail.com");

        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size(), is(2));
    }
}