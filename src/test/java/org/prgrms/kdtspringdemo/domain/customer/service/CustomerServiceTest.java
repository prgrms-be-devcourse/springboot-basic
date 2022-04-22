package org.prgrms.kdtspringdemo.domain.customer.service;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerJdbcRepository;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Configuration
    @EnableTransactionManagement
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
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

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerService(customerRepository);
        }

    }

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    @AfterEach
    void dataCleanup() {
        customerRepository.deleteAll();
    }

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;
    
    @Test
    @DisplayName("repository 사이즈가 0 일때 count 테스트")
    public void  zeroCountTest() throws Exception{
        // given
        
        // when
        int count = customerService.count();

        // then
        assertThat(count, is(0));
        
    }

    @Test
    @DisplayName("맞는 아이디로 Customer 를 찾는 경우 테스트")
    public void findByIdTest () throws Exception{
        // given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "test-user", "test-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        Optional<Customer> idCustomer = customerService.findById(id);

        // then
        assertThat(customer, samePropertyValuesAs(idCustomer));

    }

    @Test
    @DisplayName("잘못된 아이디로 Customer 를 찾는 경우 테스트")
    public void findByIdTestError () throws Exception{
        // given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "test-user", "test-user@gmail.com", LocalDateTime.now());
        customerRepository.insert(customer);

        // when
        UUID wrongId = UUID.randomUUID();
        Optional<Customer> idCustomer = customerService.findById(wrongId);

        // then
        assertThat(customer, samePropertyValuesAs(Optional.of(null)));
    }

}